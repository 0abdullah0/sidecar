package com.ropulva.sidecars.utils.response;

public class StandardResponse<T> {

	final private int code;
	final private T message;

	public StandardResponse(T message, int code) {
		this.message = message;
		this.code = code;
	}

	public T getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}
}
