package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/** Adapter dell'interfaccia UserDetailsService di Spring Security */
@Component
public class UserDetailsAdapter implements UserDetailsService {
  private final FindAccountUseCase findAccountUseCase;

  /**
   * Il costruttore
   *
   * @param findAccountUseCase il caso d'uso per ottentere l'utente, dato l'username
   */
  public UserDetailsAdapter(FindAccountUseCase findAccountUseCase) {
    this.findAccountUseCase = findAccountUseCase;
  }

  /**
   * Resituisce se l'utente esiste oppure no
   *
   * @param username l'username dell'utente da cercare
   * @return un'istanza di UserDetails, con i dettagli dell'utente trovato
   * @throws UsernameNotFoundException se l'utente non è stato trovato
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      return findAccountUseCase
          .findByUsername(username)
          .map(account -> new CustomAccountDetails(account))
          .orElseThrow(() -> new UsernameNotFoundException(("usernameNotFound")));
    } catch (BusinessException e) {
      throw new UsernameNotFoundException(("usernameNotFound"));
    }
  }
}
