package com.revolut.moneytransfer.exception.model;

import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionResponse {

	private static final long serialVersionUID = 1L;

	private String exceptionStackTrace;

	private int errorCode;

	public ExceptionResponse() {
	}

	public ExceptionResponse(String exceptionStackTrace, int errorCode) {
		super();
		this.exceptionStackTrace = exceptionStackTrace;
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + errorCode;
		result = prime * result + ((exceptionStackTrace == null) ? 0 : exceptionStackTrace.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionResponse other = (ExceptionResponse) obj;
		if (errorCode != other.errorCode)
			return false;
		if (exceptionStackTrace == null) {
			if (other.exceptionStackTrace != null)
				return false;
		} else if (!exceptionStackTrace.equals(other.exceptionStackTrace))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [exceptionStackTrace=" + exceptionStackTrace + ", errorCode=" + errorCode + "]";
	}

}
