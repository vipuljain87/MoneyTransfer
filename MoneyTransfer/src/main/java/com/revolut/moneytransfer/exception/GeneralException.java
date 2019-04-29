package com.revolut.moneytransfer.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.revolut.moneytransfer.exception.model.ExceptionResponse;

@Provider
public class GeneralException extends RuntimeException implements ExceptionMapper<Throwable> {

	private static final long serialVersionUID = 100L;

	public GeneralException(String message) {
		super(message);
	}

	public GeneralException(String message, Throwable t) {
		super(message, t);
	}

	public Response toResponse(Throwable t) {
		return Response.status(500).entity(new ExceptionResponse(t.getMessage(), 500)).build();
	}
}
