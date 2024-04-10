package com.ropulva.sidecars.exception;

public class ExpiredAuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpiredAuthException(String message) {
		super(message);
	}
}
