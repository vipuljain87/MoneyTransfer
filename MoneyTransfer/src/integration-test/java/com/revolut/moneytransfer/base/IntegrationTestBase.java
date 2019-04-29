package com.revolut.moneytransfer.base;

import org.apache.http.client.utils.URIBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IntegrationTestBase extends TestBase {

	protected ObjectMapper mapper = new ObjectMapper();
	protected URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:8082");
}
