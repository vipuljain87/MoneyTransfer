package com.revolut.moneytransfer.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.revolut.moneytransfer.exception.model.ExceptionResponse;

@Provider
public class MoneyTransferException extends Exception implements ExceptionMapper<Throwable> {

	private static final long serialVersionUID = 100L;

	public MoneyTransferException(String message) {
		super(message);
	}

	public MoneyTransferException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

	public Response toResponse(Throwable t) {
		ExceptionResponse te = new ExceptionResponse(t.getMessage(), 500);
		return Response.status(500).entity(te).build();
	}

}
