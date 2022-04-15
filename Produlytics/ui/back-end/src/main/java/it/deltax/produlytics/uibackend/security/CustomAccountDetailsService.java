package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Il service per verificare se l'utente che sta eseguendo l'autenticazione esiste
 */
@Service
public class CustomAccountDetailsService implements UserDetailsService {
	@Autowired
	private final AccountRepository repo;

	/**
	 * Il costruttore
	 * @param repo lo strato di persistenza con i dati sugli utenti
	 */
	public CustomAccountDetailsService(AccountRepository repo) {
		this.repo = repo;
	}

	/**
	 * Resituisce se l'utente esiste oppure no
	 * @param username l'username dell'utente da cercare
	 * @return i dettagli dell'utente trovato
	 * @throws UsernameNotFoundException se l'utente non è stato trovato
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findById(username).map(CustomAccountDetails::new)
			.orElseThrow(() -> new UsernameNotFoundException(("usernameNotFound")));
	}
}
