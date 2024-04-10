package com.ropulva.sidecars.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ropulva.sidecars.dto.NotificationMessageDto;
import com.ropulva.sidecars.service.INotificationService;
import com.ropulva.sidecars.utils.response.StandardResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/notification")
@Tag(name = "Notification", description = "Notification functiolaity tasks")
@Slf4j
public class NotificationControler {

	private final INotificationService iNotificationService;

	@Autowired
	public NotificationControler(INotificationService iNotificationService) {
		this.iNotificationService = iNotificationService;
	}

	@PostMapping("/sendAll")
	@Operation(summary = "send message...")
	public StandardResponse<String> sendAll(@RequestBody @Valid NotificationMessageDto body)
			throws FirebaseMessagingException {
		log.info("Starting {sendAll}...");
		iNotificationService.sendNotificationToAll(body);
		log.info("{sendAll} Completed ^_^");
		return new StandardResponse<String>("Notification sent successfully", HttpStatus.OK.value());

	}

	@PostMapping("/sendGroup")
	@Operation(summary = "send message...")
	public StandardResponse<List<String>> sendGroup(@RequestBody @Valid NotificationMessageDto body)
			throws FirebaseMessagingException {
		log.info("Starting {sendGroup}...");
		final List<String> ids = iNotificationService.sendNotificationToGroup(body);
		log.info("{sendGroup} Completed ^_^");
		return new StandardResponse<List<String>>(ids, HttpStatus.OK.value());

	}

}
