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
import com.ropulva.sidecars.service.ISMSSenderService;
import com.ropulva.sidecars.utils.PINRandomGenerator;

@Service
public class OtpService implements IOtpService {

	private final OtpRepository otpRepository;
	private final ISMSSenderService smsSenderService;

	OtpService(OtpRepository otpRepository, ISMSSenderService smsSenderService) {
		this.otpRepository = otpRepository;
		this.smsSenderService = smsSenderService;
	}

	@Override
	public void createOtp(String countryCode, String phoneNumber, String appId) {
		try {

			PINRandomGenerator pinRandomGenerator = new PINRandomGenerator();
			String pinCode = pinRandomGenerator.generatePINCode();

			smsSenderService.sendSms(pinCode, countryCode, phoneNumber);

			otpRepository.save(phoneNumber, appId, pinCode);

		} catch (CustomException e) {

			throw new CustomException(e.getMessage());

		}

	}

	@Override
	public void verifyOtp(String phoneNumber, String pinCode, String appId) {
		try {

			final OtpCodeDto otpCodeDto = otpRepository.findOtpById(phoneNumber, appId);

			final LocalTime nowTime = LocalTime.now();

			if (otpCodeDto == null || Math
					.abs(calcDiffInMinutes(nowTime, otpCodeDto.getExpireFrom())) >= SystemConstants.TIME_TO_LIVE_CODE) {
				throw new ExpiredOtpException();
			}
			if (!otpCodeDto.getCode().equals(pinCode)) {
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
