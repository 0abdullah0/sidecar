package com.ropulva.sidecars.exception;

public class AlreadyFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlreadyFoundException(String message) {

		super(message);
	}
}
