package com.revolut.moneytransfer.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import com.revolut.moneytransfer.base.IntegrationTestBase;
import com.revolut.moneytransfer.exception.model.ExceptionResponse;
import com.revolut.moneytransfer.service.impl.TransactionFactsImpl;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionServiceIntegrationTest extends IntegrationTestBase {

	private static Logger LOGGER = Logger.getLogger(TransactionServiceIntegrationTest.class);

	@Test
	public void testSuccessfullTransfer() throws URISyntaxException, ClientProtocolException, IOException {

		URI uri = builder.setPath("/transaction/2/3/200").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpPost request = new HttpPost(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 200);
	}

	@Test
	public void testUnSuccessfullTransfer() throws URISyntaxException, ClientProtocolException, IOException {

		URI uri = builder.setPath("/transaction/1/2/600").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpPost request = new HttpPost(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 500);
		String errorResponse = EntityUtils.toString(response.getEntity());
		ExceptionResponse exceptionResponse = mapper.readValue(errorResponse, ExceptionResponse.class);
		Assert.assertEquals(500, exceptionResponse.getErrorCode());
		Assert.assertEquals("Insufficient/Negative Balance. Can't initiate transfer ",
				exceptionResponse.getExceptionStackTrace());
	}

	@Test
	public void testUnSuccessfullTransferWithNegativeAmount()
			throws URISyntaxException, ClientProtocolException, IOException {

		URI uri = builder.setPath("/transaction/1/2/-600").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpPost request = new HttpPost(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 500);
		String errorResponse = EntityUtils.toString(response.getEntity());
		ExceptionResponse exceptionResponse = mapper.readValue(errorResponse, ExceptionResponse.class);
		Assert.assertEquals(500, exceptionResponse.getErrorCode());
		Assert.assertEquals("Insufficient/Negative Balance. Can't initiate transfer ",
				exceptionResponse.getExceptionStackTrace());
	}

	@Test
	public void testUnSuccessfullTransferWithZeroAmount()
			throws URISyntaxException, ClientProtocolException, IOException {

		URI uri = builder.setPath("/transaction/1/2/0").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpPost request = new HttpPost(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 500);
		String errorResponse = EntityUtils.toString(response.getEntity());
		ExceptionResponse exceptionResponse = mapper.readValue(errorResponse, ExceptionResponse.class);
		Assert.assertEquals(500, exceptionResponse.getErrorCode());
		Assert.assertEquals("Zero Amount, Please try with correct amount", exceptionResponse.getExceptionStackTrace());
	}

	@Test
	public void testMultithreadedTransaction()
			throws URISyntaxException, ClientProtocolException, IOException, InterruptedException {
		final TransactionFactsImpl txnfactsImpl = factory.getTransactionFactsImpl();
		final CountDownLatch latch = new CountDownLatch(50);
		for (int i = 0; i < 50; i++) {
			new Thread(new Runnable() {

				public void run() {
					try {
						txnfactsImpl.transfer(1, 2, new BigDecimal(10.00));
					} catch (Exception e) {
						LOGGER.error("Error occurred during transfer ", e);
					} finally {
						latch.countDown();
					}
				}
			}).start();
		}

		latch.await();

		BigDecimal balance1 = factory.getAccountFactsImpl().getAccountBalance(1);
		Assert.assertEquals(BigDecimal.ZERO, balance1.setScale(0));
		BigDecimal balance2 = factory.getAccountFactsImpl().getAccountBalance(2);
		Assert.assertEquals(new BigDecimal("500.00"), balance2);
	}

}
