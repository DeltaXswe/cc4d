package it.deltax.produlytics.uibackend.accounts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Classe per la configurazione di Spring che descrive come creare il cifratore. */
@Configuration
public class EncoderConfig {
  /**
   * Crea un'istanza di BCryptPasswordEncoder
   *
   * @return la nuova istanza di BCryptPasswordEncoder
   */
  @Bean
  public BCryptPasswordEncoder getEncoder() {
    return new BCryptPasswordEncoder();
  }
}
