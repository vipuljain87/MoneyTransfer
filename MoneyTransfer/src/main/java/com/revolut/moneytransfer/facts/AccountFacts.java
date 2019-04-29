package com.revolut.moneytransfer.facts;

import java.math.BigDecimal;
import java.util.List;

import com.revolut.moneytransfer.model.Account;

public interface AccountFacts {

	public List<Account> getAccounts();

	public Account getAccountByAccountNumber(Integer accountNumber);

	public BigDecimal getAccountBalance(Integer accountNumber);

}
