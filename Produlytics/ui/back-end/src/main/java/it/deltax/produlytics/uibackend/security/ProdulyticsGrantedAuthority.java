package it.deltax.produlytics.uibackend.security;

import org.springframework.security.core.GrantedAuthority;

public enum ProdulyticsGrantedAuthority implements GrantedAuthority {
	ADMIN,
	ACCOUNT;

	@Override
	public String getAuthority() {
		return String.valueOf(this);
	}
}
