package com.ropulva.sidecars.service;

public interface IOtpService {

	void createOtp(String phoneNumber, String appId);

	void verifyOtp(String phoneNumber, String code, String appId);

}
