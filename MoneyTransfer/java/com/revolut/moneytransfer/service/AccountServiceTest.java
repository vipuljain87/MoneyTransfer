package com.revolut.moneytransfer.service;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.revolut.moneytransfer.db.DBFactory;
import com.revolut.moneytransfer.db.model.Database;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.test.TestBase;

import org.junit.Assert;

@RunWith(JUnit4.class)
public class AccountServiceTest extends TestBase{
	
	private static final Optional<? extends Database> factory = new DBFactory().getDBFactory("H2");

	private static Logger LOGGER = Logger.getLogger(AccountServiceTest.class);

    @InjectMocks
    AccountService accountService;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testGetAllAccounts() {
		Response res = accountService.getAccounts();
		List<Account> accounts = (List<Account>) res.getEntity();
		System.out.println(res.getStatus());
		Assert.assertEquals(3, accounts.size());
	}
}
