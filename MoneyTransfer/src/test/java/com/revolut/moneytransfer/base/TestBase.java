package com.revolut.moneytransfer.base;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.revolut.moneytransfer.db.AbstractFactory;
import com.revolut.moneytransfer.db.H2Factory;
import com.revolut.moneytransfer.jetty.JettyServer;

public class TestBase {

	private static Logger LOGGER = Logger.getLogger(IntegrationTestBase.class);
	protected static final AbstractFactory factory = AbstractFactory.getDBFactory("H2");
	private static JettyServer server;

	@BeforeClass
	public static void init() {
		((H2Factory) factory).initDB();
	}

	@BeforeClass
	public static void initServer() {

		server = new JettyServer();
		try {
			server.start(8082);
		} catch (Exception e) {
			try {
				LOGGER.info("... Stopping Server ... ");
				server.stop();
			} catch (Exception e1) {
			}
		}

	}

	@AfterClass
	public static void destroy() {
		try {
			server.stop();
		} catch (Exception e) {
			try {
				LOGGER.info("... Exception Stopping Server ... ");
			} catch (Exception e1) {

			}
		}
	}
}
