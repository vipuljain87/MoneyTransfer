package com.revolut.moneytransfer.facts;

import java.math.BigDecimal;

import com.revolut.moneytransfer.exception.MoneyTransferException;
import com.revolut.moneytransfer.model.Transaction;

public interface TransactionFacts {

	public Transaction transfer(Integer from, Integer to, BigDecimal amount) throws MoneyTransferException;

}
