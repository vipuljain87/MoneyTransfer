package com.revolut.moneytransfer.validation;

import java.math.BigDecimal;

import com.revolut.moneytransfer.exception.MoneyTransferException;
import com.revolut.moneytransfer.model.Account;

public interface AccountValidator {

	public Boolean validate(Account account) throws MoneyTransferException;

	public Boolean validate(Account account1, Account account2) throws MoneyTransferException;
}
