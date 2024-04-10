package com.ropulva.sidecars.service.impl;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.ropulva.sidecars.constant.SystemConstants;
import com.ropulva.sidecars.dto.OtpCodeDto;
import com.ropulva.sidecars.exception.CustomException;
import com.ropulva.sidecars.exception.ExpiredOtpException;
import com.ropulva.sidecars.exception.InvalidOtpException;
import com.ropulva.sidecars.repository.OtpRepository;
import com.ropulva.sidecars.service.IOtpService;

@Service
public class OtpService implements IOtpService {

	private final OtpRepository otpRepository;

	OtpService(OtpRepository otpRepository) {
		this.otpRepository = otpRepository;
	}

	@Override
	public void createOtp(String phoneNumber, String appId) {
		try {
			// logic to get code from provider
			otpRepository.save(phoneNumber, appId, "1234");

		} catch (CustomException e) {

			throw new CustomException(e.getMessage());

		}

	}

	@Override
	public void verifyOtp(String phoneNumber, String code, String appId) {
		try {

			final OtpCodeDto otpCodeDto = otpRepository.findOtpById(phoneNumber, appId);

			final LocalTime nowTime = LocalTime.now();

			if (otpCodeDto == null || Math
					.abs(calcDiffInMinutes(nowTime, otpCodeDto.getExpireFrom())) >= SystemConstants.TIME_TO_LIVE_CODE) {
				throw new ExpiredOtpException();
			}
			if (!otpCodeDto.getCode().equals(code)) {
				throw new InvalidOtpException();
			}

		} catch (Exception e) {

			throw new CustomException(e.getMessage());
		}

	}

	long calcDiffInMinutes(LocalTime nowTime, LocalTime creationTime) {
		return MINUTES.between(nowTime, creationTime);
	}

}
