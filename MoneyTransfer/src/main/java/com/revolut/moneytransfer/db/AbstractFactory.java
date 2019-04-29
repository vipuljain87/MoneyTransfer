package com.revolut.moneytransfer.db;

import com.revolut.moneytransfer.service.impl.AccountFactsImpl;
import com.revolut.moneytransfer.service.impl.TransactionFactsImpl;
import com.revolut.moneytransfer.service.impl.UserFactsImpl;

public abstract class AbstractFactory {

	public abstract UserFactsImpl getUserFactsImpl();

	public abstract AccountFactsImpl getAccountFactsImpl();

	public abstract TransactionFactsImpl getTransactionFactsImpl();

	public static AbstractFactory getDBFactory(String database) {

		if ("H2".equalsIgnoreCase(database)) {
			return new H2Factory();
		} else {
			return new H2Factory();
		}
	}

}
