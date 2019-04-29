package com.revolut.moneytransfer.test;

import java.sql.SQLException;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.revolut.moneytransfer.db.DBFactory;
import com.revolut.moneytransfer.db.H2Database;
import com.revolut.moneytransfer.db.model.Database;

public class TestBase {

	private static Logger LOGGER = Logger.getLogger(TestBase.class);
	private static final Optional<? extends Database> factory = new DBFactory().getDBFactory("H2");

	@BeforeClass
	public static void init() {
		H2Database h2db = (H2Database) factory.get();
		h2db.initDB();
	}

	@Before
	public void initServer() {
		Server webServer;
		try {
			webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
			webServer.start();
		} catch (SQLException e) {
			LOGGER.error("Exception while starting server.", e);
		}

	}

	@AfterClass
	public static void destroy() {

	}
}
