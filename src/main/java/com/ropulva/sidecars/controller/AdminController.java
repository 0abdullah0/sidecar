package com.ropulva.sidecars.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ropulva.sidecars.dto.RopulvaAppDto;
import com.ropulva.sidecars.service.IAdminService;
import com.ropulva.sidecars.utils.response.StandardResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/admin")
@Tag(name = "Admin", description = "Admin functiolaity tasks")
@Slf4j
public class AdminController {

	private final IAdminService iAdminService;

	@Autowired
	public AdminController(IAdminService iAdminService) {
		this.iAdminService = iAdminService;
	}

	@GetMapping(value = "/ropulva_apps")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "list ropulva apps...")
	public StandardResponse<List<RopulvaAppDto>> getRopulvaApps() {
		log.info("Starting {getRopulvaApps}...");
		final List<RopulvaAppDto> apps = iAdminService.findAllApps();
		log.info("{getRopulvaApps} Completed ^_^");
		return new StandardResponse<List<RopulvaAppDto>>(apps, HttpStatus.OK.value());
	}

	@PostMapping(value = "/create_app")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "create new ropulva app...")
	public StandardResponse<String> createApp(@RequestBody RopulvaAppDto appDto) {
		log.info("Starting {createApp}...");
		iAdminService.createApp(appDto);
		log.info("{createApp} Completed ^_^");
		return new StandardResponse<String>("App created succesfully", HttpStatus.CREATED.value());
	}

}
