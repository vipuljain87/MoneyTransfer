package com.revolut.moneytransfer;

import org.apache.log4j.Logger;

import com.revolut.moneytransfer.db.AbstractFactory;
import com.revolut.moneytransfer.db.H2Factory;
import com.revolut.moneytransfer.jetty.JettyServer;

public class Main {
	private static Logger LOGGER = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		LOGGER.info("... Initializing DB ... ");
		AbstractFactory factory = AbstractFactory.getDBFactory("H2");
		H2Factory h2db = (H2Factory) factory;
		h2db.initDB();
		LOGGER.info("... Finished Initializing DB ... ");

		LOGGER.info("... Starting Server ... ");
		JettyServer server = new JettyServer();
		try {
			server.start(8080);
		} catch (Exception e) {
			try {
				LOGGER.info("... Stopping Server ... ");
				server.stop();
			} catch (Exception e1) {
			}
		}
	}

}
