package com.ropulva.sidecars.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ropulva.sidecars.constant.SystemConstants;
import com.ropulva.sidecars.service.ISMSSenderService;

@Service
public class SMSSenderService implements ISMSSenderService {

	@Value("${sms.userId}")
	private String userId;

	@Value("${sms.pwd}")
	private String pwd;

	@Value("${sms.environment}")
	private String environment;

	private final WebClient smsApiClient;

	@Autowired
	public SMSSenderService(WebClient smsApiClient) {
		this.smsApiClient = smsApiClient;
	}

	@Override
	public void sendSms(String pinCode, String countryCode, String phoneNumber, String senderId) {

		System.out.println(pinCode + '-' + countryCode + '-' + phoneNumber + '-' + senderId);

		final Object result = smsApiClient.post()
				.uri("?environment=" + environment + "&username=" + userId + "&password=" + pwd + "&sender=" + senderId
						+ "&mobile=" + countryCode + phoneNumber + "&template=" + SystemConstants.SMS_TEMPLATE + "&otp="
						+ pinCode)
				.header("content-type", "application/json").retrieve().bodyToMono(Object.class).block();

		System.out.println(result.toString());

	}

}
