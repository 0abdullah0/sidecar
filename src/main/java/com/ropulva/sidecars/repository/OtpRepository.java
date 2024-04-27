package com.ropulva.sidecars.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ropulva.sidecars.dto.OtpCodeDto;

@Repository
public class OtpRepository {

	public static final String HASH_KEY = "OtpCode";

	private final RedisTemplate<String, OtpCodeDto> redisTemplate;

	@Autowired
	OtpRepository(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(String countryCode, String phoneNumber, String appId, String pinCode) {
		final OtpCodeDto otpCode = new OtpCodeDto();
		otpCode.setId(appId + "_" + countryCode + phoneNumber);
		otpCode.setCode(pinCode);
		otpCode.setPhoneNumber(countryCode + phoneNumber);
		otpCode.setExpireFrom(LocalTime.now());
		redisTemplate.opsForHash().put(HASH_KEY, otpCode.getId(), otpCode);
		redisTemplate.expire(otpCode.getId(), 1, TimeUnit.SECONDS);

	}

	public OtpCodeDto findOtpById(String countryCode, String phoneNumber, String appId) {
		return (OtpCodeDto) redisTemplate.opsForHash().get(HASH_KEY, appId + "_" + countryCode + phoneNumber);
	}

	public void delete(String pinCode) {
		redisTemplate.opsForHash().delete(HASH_KEY, pinCode);
	}

	public List<OtpCodeDto> findAll() {
		return (List<OtpCodeDto>) (Object) redisTemplate.opsForHash().values(HASH_KEY);
	}

}
