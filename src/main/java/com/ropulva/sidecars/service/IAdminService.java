package com.ropulva.sidecars.service;

import java.util.List;

import com.ropulva.sidecars.dto.RopulvaAppDto;

public interface IAdminService {

	void createApp(RopulvaAppDto appDto);

	RopulvaAppDto findApp(String appId);

	List<RopulvaAppDto> findAllApps();

}
