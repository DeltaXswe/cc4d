package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/** Classe per la configurazione di Spring che descrive come creare le classi di business. */
@Configuration
public class UserDetailsAdapterConfiguration {
  /**
   * Crea un'istanza di UserDetailsService
   *
   * @param findAccountUseCase la porta per trovare un utente, da passare al costruttore di
   *     UserDetailsAdapter
   * @return la nuova istanza di UserDetailsAdapter
   */
  @Bean
  UserDetailsService userDetailsService(FindAccountUseCase findAccountUseCase) {
    return new UserDetailsAdapter(findAccountUseCase);
  }
}
