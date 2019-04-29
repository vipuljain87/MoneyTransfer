package com.revolut.moneytransfer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.revolut.moneytransfer.base.IntegrationTestBase;

@RunWith(JUnit4.class)
public class H2DatabaseTest extends IntegrationTestBase {

	private static final AbstractFactory factory = AbstractFactory.getDBFactory("H2");

	private static Logger LOGGER = Logger.getLogger(H2DatabaseTest.class);

	@Test
	public void testH2Database() throws SQLException {

		String sql = "SELECT * FROM USER";
		PreparedStatement ps;
		ResultSet rs = null;
		int count = 0;
		Optional<Connection> conn = ((H2Factory) factory).getConnection();
		if (conn.isPresent()) {
			Connection c = conn.get();
			try {
				c.setAutoCommit(false);
				ps = c.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					LOGGER.debug("Username::" + rs.getString("UserName"));
					count++;
				}
			} catch (SQLException e) {
				LOGGER.error("Exception while executing query.", e);
			}

		} else {
			LOGGER.error("Could not establish connection with database");
		}

		Assert.assertEquals(3, count);

	}
}
