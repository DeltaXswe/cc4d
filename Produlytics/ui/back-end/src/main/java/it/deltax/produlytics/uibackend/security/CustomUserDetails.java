package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Classe che rappresenta un utente in Spring Security
 * @author Leila Dardouri
 */
public class CustomUserDetails implements UserDetails {
	private AccountEntity account;

	/**
	 * Il costruttore
	 * @param account l'utente contenente tutte le sue informazioni
	 */
	public CustomUserDetails(AccountEntity account){
		this.account = account;
	}

	/**
	 * Restituisce l'username dell'utente
	 * @return una stringa rappresentante l'username dell'utente
	 */
	@Override
	public String getUsername() {
		return this.account.getUsername();
	}

	/**
	 * Restituisce la password dell'utente
	 * @return una stringa rappresentante la password dell'utente
	 */
	@Override
	public String getPassword() {
		System.out.println("getPassword");
		return this.account.getHashedPassword();
	}

	/**
	 * Restituisce i permessi di cui dispone l'utente
	 * @return una collezione rappresentante i permessi di cui dispone l'utente
	 */
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

	/**
	 * Restituisce se l'utente non è scaduto
	 * @return true
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Restituisce se l'utente non è bloccato
	 * @return true
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Restituisce se le credenziali non sono scadute
	 * @return true
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Restituisce se l'utente non è archiviato
	 * @return true se non è archiviato; false, altrimenti
	 */
	@Override
	public boolean isEnabled() {
		return !this.account.getArchived();
	}
}
