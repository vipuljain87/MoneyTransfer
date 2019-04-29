package com.revolut.moneytransfer.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.revolut.moneytransfer.service.AccountService;
import com.revolut.moneytransfer.service.TransactionService;
import com.revolut.moneytransfer.service.UserService;

public class JettyServer {

	private Server server;

	public void start(int port) throws Exception {

		server = new Server(port);
		ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContextHandler.setContextPath("/");
		server.setHandler(servletContextHandler);

		ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/*");
		servletHolder.setInitParameter("jersey.config.server.provider.classnames", UserService.class.getCanonicalName()
				+ "," + AccountService.class.getCanonicalName() + "," + TransactionService.class.getCanonicalName());

		server.start();

	}

	public void stop() throws Exception {
		server.stop();
	}

}
