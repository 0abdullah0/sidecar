package com.ropulva.sidecars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ropulva.sidecars.dto.RopulvaAdminDto;
import com.ropulva.sidecars.service.IAuthService;
import com.ropulva.sidecars.utils.response.StandardResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Auth", description = "Auth functiolaity tasks")
@Slf4j
public class AuthController {

	private final IAuthService iAuthService;

	@Autowired
	public AuthController(IAuthService iAuthService) {
		this.iAuthService = iAuthService;
	}

	@PostMapping("/login")
	@Operation(summary = "login to admin portal...")
	public StandardResponse<String> login(@RequestBody RopulvaAdminDto admin) {
		log.info("Starting {login}...");
		final String token = iAuthService.login(admin);
		log.info("{login} Completed ^_^");
		return new StandardResponse<String>(token, HttpStatus.OK.value());
	}

	@PostMapping("/signup")
	@Operation(summary = "signup to admin portal...")
	public StandardResponse<String> signup(@RequestBody RopulvaAdminDto admin) {
		log.info("Starting {signup}...");
		iAuthService.signup(admin);
		log.info("{signup} Completed ^_^");
		return new StandardResponse<String>("Signed up successfully", HttpStatus.CREATED.value());
	}

}
