package com.ropulva.sidecars.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ropulva.sidecars.dto.RopulvaAppDto;
import com.ropulva.sidecars.exception.AlreadyFoundException;
import com.ropulva.sidecars.exception.NotFoundException;
import com.ropulva.sidecars.model.RopulvaApp;
import com.ropulva.sidecars.repository.RopulvaAppRepository;
import com.ropulva.sidecars.service.IAdminService;

@Service
public class AdminService extends BaseService implements IAdminService {

	private final RopulvaAppRepository ropulvaAppRepository;

	AdminService(ModelMapper modelMapper, RopulvaAppRepository ropulvaAppRepository) {
		super(modelMapper);
		this.ropulvaAppRepository = ropulvaAppRepository;
	}

	@Override
	public void createApp(RopulvaAppDto appDto) {

		try {
			final RopulvaAppDto ropulvaAppDto = findApp(appDto.getId());
			if (ropulvaAppDto != null) {
				throw new AlreadyFoundException("App already exist!");
			}
		} catch (NotFoundException e) {

			RopulvaApp app = convertModelTo(appDto, RopulvaApp.class);

			ropulvaAppRepository.save(app);

		}

	}

	@Override
	public RopulvaAppDto findApp(String appId) {

		Optional<RopulvaApp> app = ropulvaAppRepository.findById(appId);

		if (app.isEmpty()) {

			throw new NotFoundException("App doesn't exist!");

		}

		RopulvaAppDto appDto = convertModelTo(app.get(), RopulvaAppDto.class);
		return appDto;

	}

	@Override
	public List<RopulvaAppDto> findAllApps() {
		List<RopulvaApp> ropulvaApps = ropulvaAppRepository.findAll();
		List<RopulvaAppDto> ropulvaAppsDto = ropulvaApps.stream().map(app -> convertModelTo(app, RopulvaAppDto.class))
				.collect(Collectors.toList());
		return ropulvaAppsDto;
	}

}
