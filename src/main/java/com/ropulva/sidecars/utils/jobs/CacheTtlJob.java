package com.ropulva.sidecars.utils.jobs;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ropulva.sidecars.constant.SystemConstants;
import com.ropulva.sidecars.dto.OtpCodeDto;
import com.ropulva.sidecars.repository.OtpRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CacheTtlJob {

	@Autowired
	OtpRepository otpRepository;

	@Scheduled(fixedRate = 60 * 60 * 1000) // 1hr
	@Async
	public void scheduleCacheTtlJob() {
		log.info("Starting {scheduleCacheTtlJob}...");
		invalidateExpiredCaches();
		log.info("{scheduleCacheTtlJob} Compeleted ^_^");

	}

	public void invalidateExpiredCaches() {
		final LocalTime nowTime = LocalTime.now();

		List<OtpCodeDto> otpCodes = otpRepository.findAll();

		otpCodes.stream().parallel().forEach(otpCode -> {

			if (Math.abs(calcDiffInMinutes(nowTime, otpCode.getExpireFrom())) >= SystemConstants.TIME_TO_LIVE_CODE) {
				otpRepository.delete(otpCode.getId());
			}

		});

	}

	long calcDiffInMinutes(LocalTime nowTime, LocalTime creationTime) {
		return MINUTES.between(nowTime, creationTime);
	}

}
