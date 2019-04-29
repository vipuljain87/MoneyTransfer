package com.revolut.moneytransfer.validation.impl;

import java.math.BigDecimal;

import com.revolut.moneytransfer.exception.MoneyTransferException;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.validation.AccountValidator;

public class AccountValidatorImpl implements AccountValidator {

	public Boolean validate(Account account) throws MoneyTransferException {

		if (account == null)
			throw new MoneyTransferException("Couldn't find the account. Please check for valid account");

		if (BigDecimal.ZERO.compareTo(account.getAccountBalance()) > 0) {
			throw new MoneyTransferException("Insufficient Balance for account: " + account.getAccountNumber());
		}

		return true;
	}

	public Boolean validate(BigDecimal amount) throws MoneyTransferException {
		if (BigDecimal.ZERO.compareTo(amount) == 0) {
			throw new MoneyTransferException("Zero Amount, Please try with correct amount");
		}

		if (BigDecimal.ZERO.compareTo(amount) > 0) {
			throw new MoneyTransferException("Insufficient/Negative Balance. Can't initiate transfer ");
		}

		return true;
	}

	public Boolean validate(Account account1, Account account2) throws MoneyTransferException {

		validate(account1);
		validate(account2);

		return true;
	}

	public void validate(BigDecimal accountBalance, BigDecimal remainingBalance) throws MoneyTransferException {
		if (BigDecimal.ZERO.compareTo(accountBalance) >= 0 && BigDecimal.ZERO.compareTo(remainingBalance) >= 0) {
			throw new MoneyTransferException("Insufficient/Negative Balance. Can't initiate transfer ");
		}

		if (!(BigDecimal.ZERO.compareTo(remainingBalance) <= 0 && BigDecimal.ZERO.compareTo(accountBalance) < 0))
			validate(remainingBalance);
	}

}
