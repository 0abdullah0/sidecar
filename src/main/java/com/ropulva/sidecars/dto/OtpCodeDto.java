package com.ropulva.sidecars.dto;

import java.io.Serializable;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import com.ropulva.sidecars.constant.SystemConstants;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash(value = "OtpCode", timeToLive = SystemConstants.TIME_TO_LIVE_CODE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpCodeDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String code;

	@NotEmpty(message = "body of notification cannot be empty or null")
	@NotNull
	private String phoneNumber;

	@TimeToLive
	private LocalTime expireFrom;

}