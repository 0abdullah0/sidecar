package com.ropulva.sidecars.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ropulva.sidecars.config.security.JwtTokenProvider;
import com.ropulva.sidecars.dto.RopulvaAdminDto;
import com.ropulva.sidecars.exception.AlreadyFoundException;
import com.ropulva.sidecars.exception.InvalidPasswordException;
import com.ropulva.sidecars.exception.NotFoundException;
import com.ropulva.sidecars.model.RopulvaAdmin;
import com.ropulva.sidecars.repository.RopulvaAdminRepository;
import com.ropulva.sidecars.service.IAuthService;

@Service
public class AuthService extends BaseService implements IAuthService {

	@Autowired
	RopulvaAdminRepository ropulvaAdminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	AuthService(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public String login(RopulvaAdminDto ropulvaAdminDto) {
		final Optional<RopulvaAdmin> admin = ropulvaAdminRepository.findByUsername(ropulvaAdminDto.getUsername());
		if (admin.isEmpty()) {
			throw new NotFoundException("Admin with this username not exist");
		}

		if (!passwordEncoder.matches(ropulvaAdminDto.getPassword(), admin.get().getPassword())) {
			throw new InvalidPasswordException();

		}

		return jwtTokenProvider.createToken(ropulvaAdminDto);
	}

	@Override
	public void signup(RopulvaAdminDto ropulvaAdminDto) {
		final Optional<RopulvaAdmin> admin = ropulvaAdminRepository.findByUsername(ropulvaAdminDto.getUsername());
		if (admin.isPresent()) {
			throw new AlreadyFoundException("Admin with this username already exist");
		}

		RopulvaAdmin ropulvaAdmin = convertModelTo(ropulvaAdminDto, RopulvaAdmin.class);
		ropulvaAdmin.setPassword(passwordEncoder.encode(ropulvaAdminDto.getPassword()));

		ropulvaAdminRepository.save(ropulvaAdmin);

	}

}
