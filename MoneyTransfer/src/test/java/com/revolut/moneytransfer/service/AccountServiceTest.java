package com.revolut.moneytransfer.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revolut.moneytransfer.base.TestBase;
import com.revolut.moneytransfer.model.Account;

@RunWith(JUnit4.class)
public class AccountServiceTest extends TestBase {

	@InjectMocks
	AccountService accountService;

	@Mock
	Account account;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllAccounts() {
		Response res = accountService.getAccounts();
		List<Account> accounts = (List<Account>) res.getEntity();
		Assert.assertEquals(200, res.getStatus());
		Assert.assertEquals(3, accounts.size());
	}

	@Test
	public void testGetAccountByAccountNumber() {
		when(account.getAccountNumber()).thenReturn(1);
		Response res = accountService.getAccountByAccountNumber(1);
		Account account = (Account) res.getEntity();
		Assert.assertEquals("Savings", account.getAccountName());
	}

	@Test
	public void testGetAccountBalance() {
		when(account.getAccountNumber()).thenReturn(1);
		Response res = accountService.getAccountByAccountNumber(1);
		Account account = (Account) res.getEntity();
		Assert.assertEquals(new BigDecimal("300.00"), account.getAccountBalance());
	}
}
