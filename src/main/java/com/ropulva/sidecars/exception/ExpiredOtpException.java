package com.ropulva.sidecars.exception;

public class ExpiredOtpException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpiredOtpException() {

		super("Expired otp code");
	}

}
