package com.revolut.moneytransfer.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revolut.moneytransfer.db.AbstractFactory;
import com.revolut.moneytransfer.db.H2Factory;
import com.revolut.moneytransfer.exception.GeneralException;
import com.revolut.moneytransfer.exception.MoneyTransferException;
import com.revolut.moneytransfer.facts.TransactionFacts;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.Transaction;
import com.revolut.moneytransfer.model.TransactionStatus;
import com.revolut.moneytransfer.utils.DBUtils;
import com.revolut.moneytransfer.validation.impl.AccountValidatorImpl;

public class TransactionFactsImpl implements TransactionFacts {

	private static final String GET_ACCOUNT_BY_ACCOUNTNUMBER = "SELECT * FROM Account WHERE AccountNumber = ? FOR UPDATE ";
	private final static String UPDATE_BALANCE_BY_ACCOUNTNUMBER = "UPDATE Account SET AccountBalance = ? WHERE AccountNumber = ? ";
	private final static String INSERT_TRANSACTION = "INSERT INTO Transaction(FromAccountNumber,ToAccountNumber,Amount,Status,Created,LastUpdated) VALUES (?,?,?,?,?,?)";
	private static final String GET_TRANSACTION_BY_ACCOUNTNUMBER = "SELECT * FROM Transaction WHERE FromAccountNumber = ? and ToAccountNumber = ? order by LastUpdated desc";
	private static final String GET_ALL_TRANSACTIONS = "SELECT * FROM Transaction";

	private static Logger LOGGER = Logger.getLogger(TransactionFactsImpl.class);

	private static final AbstractFactory factory = AbstractFactory.getDBFactory("H2");
	private AccountValidatorImpl accountValidatorImpl = new AccountValidatorImpl();

	public Transaction transfer(Integer from, Integer to, BigDecimal amount) throws MoneyTransferException {

		accountValidatorImpl.validate(amount);

		int numTransactions = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement updateStmt = null;
		ResultSet rs = null;
		Account fromAccount = null;
		Account toAccount = null;
		Transaction transaction = null;

		try {
			conn = ((H2Factory) factory).getConnection().get();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(GET_ACCOUNT_BY_ACCOUNTNUMBER);
			stmt.setInt(1, from);
			rs = stmt.executeQuery();
			if (rs.next()) {
				fromAccount = new Account(rs.getString("accountName"), rs.getInt("accountNumber"),
						rs.getString("UserName"), rs.getBigDecimal("accountBalance"), rs.getBoolean("IsSoftDeleted"),
						rs.getTimestamp("Created"), rs.getTimestamp("LastUpdated"));
				LOGGER.info("from Account::" + fromAccount);
			}

			stmt = conn.prepareStatement(GET_ACCOUNT_BY_ACCOUNTNUMBER);
			stmt.setLong(1, to);
			rs = stmt.executeQuery();
			if (rs.next()) {
				toAccount = new Account(rs.getString("accountName"), rs.getInt("accountNumber"),
						rs.getString("UserName"), rs.getBigDecimal("accountBalance"), rs.getBoolean("IsSoftDeleted"),
						rs.getTimestamp("Created"), rs.getTimestamp("LastUpdated"));
				LOGGER.info("to Account:: " + toAccount);
			}

			accountValidatorImpl.validate(fromAccount, toAccount);

			BigDecimal remainingBalance = fromAccount.getAccountBalance().subtract(amount);
			accountValidatorImpl.validate(fromAccount.getAccountBalance(), remainingBalance);

			updateStmt = conn.prepareStatement(UPDATE_BALANCE_BY_ACCOUNTNUMBER);
			updateStmt.setBigDecimal(1, remainingBalance);
			updateStmt.setInt(2, fromAccount.getAccountNumber());
			updateStmt.addBatch();
			updateStmt.setBigDecimal(1, toAccount.getAccountBalance().add(amount));
			updateStmt.setLong(2, toAccount.getAccountNumber());
			updateStmt.addBatch();

			int[] rowsUpdated = updateStmt.executeBatch();
			LOGGER.debug("rows size::" + rowsUpdated.length);
			numTransactions = rowsUpdated[0] + rowsUpdated[1];
			if (numTransactions <= 1) {
				LOGGER.debug("Number of rows updated post transaction execution ::" + numTransactions);
				throw new MoneyTransferException("Unable to execute transaction successfully");
			} else {
				PreparedStatement txnStmt = conn.prepareStatement(INSERT_TRANSACTION);
				txnStmt.setInt(1, fromAccount.getAccountNumber());
				txnStmt.setInt(2, toAccount.getAccountNumber());
				txnStmt.setBigDecimal(3, amount);
				txnStmt.setString(4, TransactionStatus.SUCCESS.name());
				txnStmt.setTimestamp(5, new Timestamp((new java.util.Date()).getTime()));
				txnStmt.setTimestamp(6, new Timestamp((new java.util.Date()).getTime()));
				txnStmt.addBatch();
				int[] executedSuccessfully = txnStmt.executeBatch();
				LOGGER.debug("Transaction row inserted into DB::" + executedSuccessfully.length);
			}
			LOGGER.info("Commiting the transaction.....");
			conn.commit();
			LOGGER.info("Commit Successfull.....");
			transaction = getTransaction(fromAccount.getAccountNumber(), toAccount.getAccountNumber());
		} catch (SQLException se) {
			LOGGER.error("Transaction Failed - Initiating RollBack::", se);
			try {
				if (conn != null) {
					transaction = getTransaction(fromAccount.getAccountNumber(), toAccount.getAccountNumber());
					conn.rollback();
					LOGGER.error("RollBack Finished.. ");
				}
				throw new MoneyTransferException("Money Transfer Failed.", se);
			} catch (SQLException re) {
				throw new MoneyTransferException("Money Transfer Failed - Failed to RollBack transaction::", re);
			}
		} finally {
			try {
				DBUtils.closeAll(conn, rs, updateStmt);
				DBUtils.close(stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return transaction;

	}

	public Transaction getTransaction(Integer fromAccountNumber, Integer toAccountNumber) {

		Optional<Connection> conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ((H2Factory) factory).getConnection();
			conn.get().setAutoCommit(false);
			stmt = conn.get().prepareStatement(GET_TRANSACTION_BY_ACCOUNTNUMBER);
			stmt.setInt(1, fromAccountNumber);
			stmt.setInt(2, toAccountNumber);
			rs = stmt.executeQuery();
			if (rs.next()) {
				Transaction transaction = new Transaction(rs.getInt("TransactionID"), rs.getInt("FromAccountNumber"),
						rs.getInt("ToAccountNumber"), rs.getBigDecimal("Amount"),
						TransactionStatus.valueOf(rs.getString("Status")), rs.getTimestamp("Created"),
						rs.getTimestamp("LastUpdated"));
				LOGGER.info("Fetched transaction: " + transaction);
				return transaction;
			} else {
				throw new GeneralException("No transaction for the user with from and to account::" + fromAccountNumber
						+ "::" + toAccountNumber);
			}
		} catch (SQLException e) {
			throw new GeneralException("Exception in reading user data", e);
		} finally {
			try {
				DBUtils.closeAll(conn.get(), rs, stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Transaction> getAllTransactions() {

		Optional<Connection> conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Transaction> transactions = new ArrayList<>();
		try {
			conn = ((H2Factory) factory).getConnection();
			conn.get().setAutoCommit(false);
			stmt = conn.get().prepareStatement(GET_ALL_TRANSACTIONS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Transaction transaction = new Transaction(rs.getInt("TransactionID"), rs.getInt("FromAccountNumber"),
						rs.getInt("ToAccountNumber"), rs.getBigDecimal("Amount"),
						TransactionStatus.valueOf(rs.getString("Status")), rs.getTimestamp("Created"),
						rs.getTimestamp("LastUpdated"));
				LOGGER.info("Fetched transaction: " + transaction);
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			throw new GeneralException("Exception in reading user data", e);
		} finally {
			try {
				DBUtils.closeAll(conn.get(), rs, stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return transactions;
	}
}
