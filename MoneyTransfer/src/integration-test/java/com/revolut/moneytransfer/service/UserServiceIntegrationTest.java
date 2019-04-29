package com.revolut.moneytransfer.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
import com.revolut.moneytransfer.exception.model.ExceptionResponse;

@RunWith(JUnit4.class)
public class UserServiceIntegrationTest extends IntegrationTestBase {

	@Test
	public void testGetAllUsers() throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = builder.setPath("/users/all").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpGet request = new HttpGet(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 200);
	}

	@Test
	public void testGetUserByUsername() throws ClientProtocolException, IOException, URISyntaxException {
		URI uri = builder.setPath("/users/vipul").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpGet request = new HttpGet(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 200);
	}

	@Test
	public void testGetUserByUsernameThatDoesntExist() throws ClientProtocolException, IOException, URISyntaxException {
		URI uri = builder.setPath("/users/abc").build();
		HttpClient client = HttpClientBuilder.create().build();

		HttpGet request = new HttpGet(uri);
		request.setHeader("Content-type", "application/json");
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		assertTrue(statusCode == 404);
		String errorResponse = EntityUtils.toString(response.getEntity());
		ExceptionResponse exceptionResponse = mapper.readValue(errorResponse, ExceptionResponse.class);
		Assert.assertEquals(404, exceptionResponse.getErrorCode());
		Assert.assertEquals("No account for the user::abc", exceptionResponse.getExceptionStackTrace());
	}

}
