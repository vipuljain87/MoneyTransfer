package com.revolut.moneytransfer.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
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
import com.revolut.moneytransfer.model.Transaction;
import com.revolut.moneytransfer.model.TransactionStatus;

@RunWith(JUnit4.class)
public class TransactionServiceTest extends TestBase {

	private static Logger LOGGER = Logger.getLogger(TransactionServiceTest.class);

	@InjectMocks
	TransactionService transactionService;

	@Mock
	Account fromAccount;

	@Mock
	Account toAccount;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testTransfer() {

		when(fromAccount.getAccountNumber()).thenReturn(1);
		when(fromAccount.getAccountBalance()).thenReturn(new BigDecimal("200.00"));
		when(toAccount.getAccountNumber()).thenReturn(2);
		when(toAccount.getAccountBalance()).thenReturn(new BigDecimal("200.00"));
		Response resp = transactionService.transfer(fromAccount.getAccountNumber(), toAccount.getAccountNumber(),
				new BigDecimal("100.00"));
		Transaction transaction = (Transaction) resp.getEntity();
		Assert.assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
	}

	/*@Test
	public void testTransferMultithreaded() throws InterruptedException {

		when(fromAccount.getAccountNumber()).thenReturn(1);
		when(fromAccount.getAccountBalance()).thenReturn(new BigDecimal("200.00"));
		when(toAccount.getAccountNumber()).thenReturn(2);
		when(toAccount.getAccountBalance()).thenReturn(new BigDecimal("200.00"));

		final CountDownLatch latch = new CountDownLatch(50);
		for (int i = 0; i < 50; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Response resp = transactionService.transfer(fromAccount.getAccountNumber(),
								toAccount.getAccountNumber(), new BigDecimal("10.00"));
						Transaction transaction = (Transaction) resp.getEntity();
						System.out.println("status::" + resp.getStatus());
					} catch (Exception e) {
						LOGGER.error("Error occurred during transfer ", e);
					} finally {
						latch.countDown();
					}
				}
			}).start();
		}

		latch.await();

		// System.out.println(fromAccount.getAccountBalance());
	}
*/
}
