package com.ropulva.sidecars.service;

public interface IOtpService {

	void createOtp(String countryCode, String phoneNumber, String appId);

	void verifyOtp(String countryCode, String phoneNumber, String pinCode, String appId);

}
