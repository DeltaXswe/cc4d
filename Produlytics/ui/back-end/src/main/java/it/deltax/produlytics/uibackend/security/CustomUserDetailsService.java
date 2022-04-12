package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Il service per verificare se l'utente che sta eseguendo l'autentiazione esiste
 * @author Leila Dardouri
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	AccountRepository repo;

	/**
	 * Resituisce se l'utente esiste oppure no
	 * @param username l'username dell'utente da cercare
	 * @return i dettagli dell'utente trovato
	 * @throws UsernameNotFoundException se l'utente non è stato trovato
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findById(username).map(CustomUserDetails::new)
			.orElseThrow(() -> new UsernameNotFoundException(("usernameNotFound")));
	}
}
