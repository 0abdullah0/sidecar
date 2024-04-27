package com.ropulva.sidecars.service.impl;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.ropulva.sidecars.constant.SystemConstants;
import com.ropulva.sidecars.dto.OtpCodeDto;
import com.ropulva.sidecars.exception.CustomException;
import com.ropulva.sidecars.exception.ExpiredOtpException;
import com.ropulva.sidecars.exception.InvalidOtpException;
import com.ropulva.sidecars.model.RopulvaApp;
import com.ropulva.sidecars.repository.OtpRepository;
import com.ropulva.sidecars.repository.RopulvaAppRepository;
import com.ropulva.sidecars.service.IOtpService;
import com.ropulva.sidecars.service.ISMSSenderService;
import com.ropulva.sidecars.utils.PINRandomGenerator;
import com.ropulva.sidecars.utils.jobs.SmsSenderTask;

@Service
public class OtpService implements IOtpService {

	private final OtpRepository otpRepository;
	private final RopulvaAppRepository ropulvaAppRepository;
	private final ISMSSenderService smsSenderService;

	private final TaskExecutor taskExecutor;

	@Autowired
	OtpService(OtpRepository otpRepository, RopulvaAppRepository ropulvaAppRepository,
			ISMSSenderService smsSenderService, TaskExecutor taskExecutor) {
		this.otpRepository = otpRepository;
		this.smsSenderService = smsSenderService;
		this.ropulvaAppRepository = ropulvaAppRepository;
		this.taskExecutor = taskExecutor;
	}

	@Override
	public void createOtp(String countryCode, String phoneNumber, String appId) {
		try {

			PINRandomGenerator pinRandomGenerator = new PINRandomGenerator();
			String pinCode = pinRandomGenerator.generatePINCode();

			RopulvaApp app = ropulvaAppRepository.findById(appId).get();

			taskExecutor
					.execute(new SmsSenderTask(countryCode, phoneNumber, app.getSenderId(), pinCode, smsSenderService));
			otpRepository.save(countryCode, phoneNumber, appId, pinCode);

		} catch (CustomException e) {

			throw new CustomException(e.getMessage());

		}

	}

	@Override
	public void verifyOtp(String countryCode, String phoneNumber, String pinCode, String appId) {
		try {

			final OtpCodeDto otpCodeDto = otpRepository.findOtpById(countryCode, phoneNumber, appId);

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
