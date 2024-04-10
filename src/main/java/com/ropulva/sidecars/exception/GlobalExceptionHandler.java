package com.ropulva.sidecars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ropulva.sidecars.utils.response.StandardResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ExpiredAuthException.class)
	public StandardResponse<String> handleExpiredAuthException(ExpiredAuthException exception) {
		return new StandardResponse<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(AlreadyFoundException.class)
	public StandardResponse<String> handleAlreadyFoundException(AlreadyFoundException exception) {
		return new StandardResponse<String>(exception.getMessage(), HttpStatus.FOUND.value());

	}

	@ExceptionHandler(NotFoundException.class)
	public StandardResponse<String> handleNotFoundException(NotFoundException exception) {
		return new StandardResponse<String>(exception.getMessage(), HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(InvalidOtpException.class)
	public StandardResponse<String> handleInvalidOtpException(InvalidOtpException exception) {
		return new StandardResponse<String>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
	}

	@ExceptionHandler(ExpiredOtpException.class)
	public StandardResponse<String> handleExpiredOtpException(ExpiredOtpException exception) {
		return new StandardResponse<String>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
	}

	@ExceptionHandler(CustomException.class)
	public StandardResponse<String> handleCustomException(CustomException exception) {
		return new StandardResponse<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public StandardResponse<String> handleInvalidPasswordException(InvalidPasswordException exception) {
		return new StandardResponse<String>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
	}
}
