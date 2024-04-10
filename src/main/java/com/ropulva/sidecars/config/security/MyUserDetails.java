package com.ropulva.sidecars.config.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ropulva.sidecars.constant.AppUserRole;
import com.ropulva.sidecars.model.RopulvaAdmin;
import com.ropulva.sidecars.repository.RopulvaAdminRepository;

@Service
public class MyUserDetails implements UserDetailsService {

	private final RopulvaAdminRepository ropulvaAdminRepository;

	MyUserDetails(RopulvaAdminRepository ropulvaAdminRepository) {
		this.ropulvaAdminRepository = ropulvaAdminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Optional<RopulvaAdmin> admin = ropulvaAdminRepository.findByUsername(username);

		if (!admin.isPresent()) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		return org.springframework.security.core.userdetails.User//
				.withUsername(username)//
				.password(admin.get().getPassword()).authorities(AppUserRole.values()).accountExpired(false)
				.accountLocked(false).credentialsExpired(false).disabled(false).build();
	}

}
