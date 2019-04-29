package com.revolut.moneytransfer.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.revolut.moneytransfer.base.IntegrationTestBase;
import com.revolut.moneytransfer.model.Account;

@RunWith(JUnit4.class)
public class AccountServiceIntegrationTest extends IntegrationTestBase {

	@Test
	public void testGetAllAccounts() throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = builder.setPath("/accounts/all").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpGet request = new HttpGet(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 200);
	}

	@Test
	public void testGetAccountByAccountNumber() throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = builder.setPath("/accounts/1").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpGet request = new HttpGet(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 200);
		String resp = EntityUtils.toString(response.getEntity());
		Account account = mapper.readValue(resp, Account.class);
		Assert.assertEquals("Savings", account.getAccountName());
	}

	@Test
	public void testGetAccountBalance() throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = builder.setPath("/accounts/1").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpGet request = new HttpGet(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 200);
		String resp = EntityUtils.toString(response.getEntity());
		Account account = mapper.readValue(resp, Account.class);
		Assert.assertEquals(new BigDecimal("300.00"), account.getAccountBalance());
	}
}
