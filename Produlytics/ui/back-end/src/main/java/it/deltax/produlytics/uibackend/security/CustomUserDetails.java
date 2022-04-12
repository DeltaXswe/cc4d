package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {
	private AccountEntity account;

	public CustomUserDetails(AccountEntity account){
		this.account = account;
	}

	@Override
	public String getUsername() {
		return this.account.getUsername();
	}

	@Override
	public String getPassword() {
		return this.account.getHashedPassword();
	}

	@Override
	public Collection<ProdulyticsGrantedAuthority> getAuthorities() {
		if(account.getAdministrator())
			return List.of(
				ProdulyticsGrantedAuthority.ACCOUNT,
				ProdulyticsGrantedAuthority.ADMIN
			);
		else
			return List.of(
				ProdulyticsGrantedAuthority.ACCOUNT);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !this.account.getArchived();
	}
}
