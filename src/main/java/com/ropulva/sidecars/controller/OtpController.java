package com.ropulva.sidecars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ropulva.sidecars.service.IOtpService;
import com.ropulva.sidecars.utils.response.StandardResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/otp")
@Tag(name = "OTP", description = "Otp functialoty tasks")
@Slf4j
public class OtpController {

	private final IOtpService iOtpService;

	@Autowired
	public OtpController(IOtpService iOtpService) {
		this.iOtpService = iOtpService;
	}

	@PostMapping("/send/{phoneNumber}")
	@Operation(summary = "send otp code...")
	public StandardResponse<String> sendOtp(@RequestParam String countryCode, @PathVariable String phoneNumber,
			@RequestHeader("appId") String appId) {
		log.info("Starting {sendOtp}...");
		iOtpService.createOtp(countryCode, phoneNumber, appId);
		log.info("{sendOtp} Completed ^_^");
		return new StandardResponse<String>("Otp code sent successfully", HttpStatus.OK.value());

	}

	@PostMapping("/verify/{phoneNumber}")
	@Operation(summary = "verify otp code...")
	public StandardResponse<String> verifyOtp(@PathVariable String phoneNumber, @RequestParam String pinCode,
			@RequestHeader("appId") String appId) {
		log.info("Starting {verifyOtp}...");
		iOtpService.verifyOtp(phoneNumber, pinCode, appId);
		log.info("{verifyOtp} Completed ^_^");
		return new StandardResponse<String>("Otp code is valid", HttpStatus.OK.value());

	}

}
