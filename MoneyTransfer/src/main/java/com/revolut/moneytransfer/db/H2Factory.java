package com.revolut.moneytransfer.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import com.revolut.moneytransfer.db.model.Database;
import com.revolut.moneytransfer.service.impl.AccountFactsImpl;
import com.revolut.moneytransfer.service.impl.TransactionFactsImpl;
import com.revolut.moneytransfer.service.impl.UserFactsImpl;
import com.revolut.moneytransfer.utils.ConfigUtil;

public class H2Factory extends AbstractFactory implements Database {

	private static Logger LOGGER = Logger.getLogger(H2Factory.class);

	private static final String H2_DRIVER = ConfigUtil.getValue("h2.driver");
	private static final String H2_CONNECTION_URL = ConfigUtil.getValue("h2.connection.url");
	private static final String H2_USERNAME = ConfigUtil.getValue("h2.user");
	private static final String H2_PASSWORD = ConfigUtil.getValue("h2.password");

	private UserFactsImpl userFactsImpl = new UserFactsImpl();
	private AccountFactsImpl accountFactsImpl = new AccountFactsImpl();
	private TransactionFactsImpl transactionFactsImpl = new TransactionFactsImpl();
	
	public Optional<String> getDriver() {
		return Optional.of(H2_DRIVER);
	}

	public Optional<String> getConnectionURL() {
		return Optional.of(H2_CONNECTION_URL);
	}

	public Optional<String> getUsername() {
		return Optional.of(H2_USERNAME);
	}

	public Optional<String> getPassword() {
		return Optional.of(H2_PASSWORD);
	}

	@Override
	public UserFactsImpl getUserFactsImpl() {
		return userFactsImpl;
	}

	@Override
	public AccountFactsImpl getAccountFactsImpl() {
		return accountFactsImpl;
	}
	
	@Override
	public TransactionFactsImpl getTransactionFactsImpl() {
		return transactionFactsImpl;
	}

	public Optional<Connection> getConnection() {
		try {
			return Optional.of(DriverManager.getConnection(H2_CONNECTION_URL, H2_USERNAME, H2_PASSWORD));
		} catch (SQLException e) {
			LOGGER.error("Exception while establishing connection with db.", e);
		}
		return Optional.empty();
	}

	public void initDB() {
		Connection conn = null;
		try {
			Class.forName(H2_DRIVER);
			conn = DriverManager.getConnection(H2_CONNECTION_URL, H2_USERNAME, H2_PASSWORD);
			RunScript.execute(conn, new FileReader("src/main/resources/testdata.sql"));
		} catch (SQLException e) {
			LOGGER.error("Exception while executing sql.", e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Exception while loading driver class.", e);
		} catch (FileNotFoundException e) {
			LOGGER.error("Exception while loading sql file.", e);
		} finally {
			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception while closing connection.", e);
			}
		}
	}

}
