package com.revolut.moneytransfer.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revolut.moneytransfer.db.AbstractFactory;
import com.revolut.moneytransfer.db.H2Factory;
import com.revolut.moneytransfer.exception.GeneralException;
import com.revolut.moneytransfer.facts.AccountFacts;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.utils.DBUtils;

public class AccountFactsImpl implements AccountFacts {

	private static Logger LOGGER = Logger.getLogger(AccountFactsImpl.class);

	private static final String GET_ALL_ACCOUNTS = "SELECT * FROM Account where IsSoftDeleted=0";
	private static final String GET_ACCOUNT_BY_ACCOUNTNUMBER = "SELECT * FROM Account WHERE AccountNumber = ? and IsSoftDeleted=0";
	private final static String INSERT_ACCOUNT = "INSERT INTO Account (AccountName,UserName,AccountBalance,IsSoftDeleted,Created,LastUpdated) VALUES (?, ?, ?, ?, ?, ?)";
	private final static String DELETE_ACCOUNT = "UPDATE Account SET IsSoftDeleted=?,LastUpdated=? WHERE AccountNumber = ? ";

	private static final AbstractFactory factory = AbstractFactory.getDBFactory("H2");

	public List<Account> getAccounts() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Account> accounts = new ArrayList<Account>();
		try {
			conn = ((H2Factory) factory).getConnection().get();
			stmt = conn.prepareStatement(GET_ALL_ACCOUNTS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Account account = new Account(rs.getString("accountName"), rs.getInt("accountNumber"),
						rs.getString("UserName"), rs.getBigDecimal("accountBalance"), rs.getBoolean("IsSoftDeleted"),
						rs.getTimestamp("Created"), rs.getTimestamp("LastUpdated"));
				accounts.add(account);
				LOGGER.info("Fetched Account: " + account);
			}
			return accounts;
		} catch (SQLException e) {
			throw new GeneralException("Exception in reading account data", e);
		} finally {
			try {
				DBUtils.closeAll(conn, rs, stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Account getAccountByAccountNumber(Integer accountNumber) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ((H2Factory) factory).getConnection().get();
			stmt = conn.prepareStatement(GET_ACCOUNT_BY_ACCOUNTNUMBER);
			stmt.setInt(1, accountNumber);
			rs = stmt.executeQuery();
			if (rs.next()) {
				Account account = new Account(rs.getString("accountName"), rs.getInt("accountNumber"),
						rs.getString("UserName"), rs.getBigDecimal("accountBalance"), rs.getBoolean("IsSoftDeleted"),
						rs.getTimestamp("Created"), rs.getTimestamp("LastUpdated"));
				LOGGER.info("Fetched Aaccount: " + account);
				return account;
			} else {
				throw new GeneralException("No Account Present for mentioned account number::" + accountNumber);
			}
		} catch (SQLException e) {
			throw new GeneralException("Exception in reading account data", e);
		} finally {
			try {
				DBUtils.closeAll(conn, rs, stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public BigDecimal getAccountBalance(Integer accountNumber) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ((H2Factory) factory).getConnection().get();
			stmt = conn.prepareStatement(GET_ACCOUNT_BY_ACCOUNTNUMBER);
			stmt.setInt(1, accountNumber);
			rs = stmt.executeQuery();
			if (rs.next()) {
				Account account = new Account(rs.getString("accountName"), rs.getInt("accountNumber"),
						rs.getString("UserName"), rs.getBigDecimal("accountBalance"), rs.getBoolean("IsSoftDeleted"),
						rs.getTimestamp("Created"), rs.getTimestamp("LastUpdated"));
				LOGGER.info("Fetched Aaccount: " + account);
				return account.getAccountBalance();
			} else {
				throw new GeneralException(
						"Unable to fetch Account Balance for the mentioned account number::" + accountNumber);
			}
		} catch (SQLException e) {
			throw new GeneralException("Unable to fetch Account Balance.", e);
		} finally {
			try {
				DBUtils.closeAll(conn, rs, stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Integer createAccount(Account account) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ((H2Factory) factory).getConnection().get();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(INSERT_ACCOUNT, java.sql.Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, account.getAccountName());
			stmt.setString(2, account.getUserName());
			stmt.setBigDecimal(3, account.getAccountBalance());
			stmt.setBoolean(4, false);
			stmt.setTimestamp(5, new Timestamp((new java.util.Date()).getTime()));
			stmt.setTimestamp(6, new Timestamp((new java.util.Date()).getTime()));
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				LOGGER.error("Account Creation Failed");
				throw new GeneralException("Account Cannot be created");
			}
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				conn.commit();
				return rs.getInt(1);
			} else {
				throw new GeneralException("Account Creation Failed");
			}

		} catch (SQLException e) {
			throw new GeneralException("Account Creation Failed::" + account, e);
		} finally {
			try {
				DBUtils.closeAll(conn, rs, stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean deleteAccount(Integer accountNumber) {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ((H2Factory) factory).getConnection().get();
			stmt = conn.prepareStatement(DELETE_ACCOUNT);
			stmt.setBoolean(1, true);
			stmt.setTimestamp(2, new Timestamp((new java.util.Date()).getTime()));
			stmt.setInt(3, accountNumber);
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				LOGGER.error("Account Deletion Failed");
				throw new GeneralException("Account Cannot be deleted");
			}
			return true;

		} catch (SQLException e) {
			throw new GeneralException("Account Deletion Failed" + accountNumber, e);
		} finally {
			try {
				DBUtils.closeAll(conn, rs, stmt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
