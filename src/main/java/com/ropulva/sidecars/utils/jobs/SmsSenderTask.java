package com.ropulva.sidecars.utils.jobs;

import com.ropulva.sidecars.service.ISMSSenderService;

public class SmsSenderTask implements Runnable {

	final ISMSSenderService smsSenderService;
	final String countryCode;
	final String phoneNumber;
	final String senderId;
	final String pinCode;

	public SmsSenderTask(String countryCode, String phoneNumber, String senderId, String pinCode,
			ISMSSenderService smsSenderService) {
		this.countryCode = countryCode;
		this.phoneNumber = phoneNumber;
		this.senderId = senderId;
		this.pinCode = pinCode;
		this.smsSenderService = smsSenderService;
	}

	@Override
	public void run() {
		smsSenderService.sendSms(pinCode, countryCode, phoneNumber, senderId);

	}

}
