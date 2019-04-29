package com.revolut.moneytransfer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.revolut.moneytransfer.db.model.Database;
import com.revolut.moneytransfer.test.TestBase;

@RunWith(JUnit4.class)
public class H2DatabaseTest extends TestBase {

	private static final Optional<? extends Database> factory = new DBFactory().getDBFactory("H2");

	private static Logger LOGGER = Logger.getLogger(H2DatabaseTest.class);

	@Test
	public void testH2Database() {

		String sql = "SELECT TOP 2 * FROM USER";
		PreparedStatement ps;

		Optional<Connection> conn = factory.get().getConnection();
		if (conn.isPresent()) {
			Connection c = conn.get();
			try {
				c.setAutoCommit(false);
				ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					LOGGER.debug("Username::" + rs.getString("UserName"));
				}
			} catch (SQLException e) {
				LOGGER.error("Exception while executing query.", e);
			}

		} else {
			LOGGER.error("Could not establish connection with database");
		}

	}
}
