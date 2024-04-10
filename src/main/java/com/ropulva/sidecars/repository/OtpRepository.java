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

	public void save(String phoneNumber, String appId, String code) {
		final OtpCodeDto otpCode = new OtpCodeDto();
		otpCode.setId(appId + "_" + phoneNumber);
		otpCode.setCode(code);
		otpCode.setPhoneNumber(phoneNumber);
		otpCode.setExpireFrom(LocalTime.now());
		redisTemplate.opsForHash().put(HASH_KEY, otpCode.getId(), otpCode);
		redisTemplate.expire(otpCode.getId(), 1, TimeUnit.SECONDS);

	}

	public OtpCodeDto findOtpById(String phoneNumber, String appId) {
		return (OtpCodeDto) redisTemplate.opsForHash().get(HASH_KEY, appId + "_" + phoneNumber);
	}

	public void delete(String otpCode) {
		redisTemplate.opsForHash().delete(HASH_KEY, otpCode);
	}

	public List<OtpCodeDto> findAll() {
		return (List<OtpCodeDto>) (Object) redisTemplate.opsForHash().values(HASH_KEY);
	}

}
