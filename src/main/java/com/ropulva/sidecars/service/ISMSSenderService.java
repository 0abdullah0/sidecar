package com.ropulva.sidecars.service;

public interface ISMSSenderService {

	void sendSms(String pinCode, String countryCode, String phoneNumber, String senderId);

}
