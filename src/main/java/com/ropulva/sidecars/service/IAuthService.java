package com.ropulva.sidecars.service;

import com.ropulva.sidecars.dto.RopulvaAdminDto;

public interface IAuthService {

	String login(RopulvaAdminDto ropulvaAdminDto);

	void signup(RopulvaAdminDto ropulvaAdminDto);

}
