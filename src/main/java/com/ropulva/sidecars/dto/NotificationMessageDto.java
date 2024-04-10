package com.ropulva.sidecars.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageDto {
	@NotEmpty(message = "title of notification cannot be empty or null")
	@NotNull
	private String title;
	@NotEmpty(message = "body of notification cannot be empty or null")
	@NotNull
	private String body;
	private String picture;
	private String payload;
	private List<String> registrationTokens;
}