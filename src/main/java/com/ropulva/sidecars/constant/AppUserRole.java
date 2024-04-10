package com.ropulva.sidecars.constant;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
	ROLE_ADMIN;

	public String getAuthority() {
		return name();
	}

}
