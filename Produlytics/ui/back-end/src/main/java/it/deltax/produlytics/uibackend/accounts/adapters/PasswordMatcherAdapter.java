package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/** L'adapter della classe BCryptPasswordEncoder per confrontare due password */
@Component
public class PasswordMatcherAdapter implements PasswordMatcherPort {
  private final BCryptPasswordEncoder encoderConfig;

  /**
   * Il costruttore
   *
   * @param encoderConfig il confrontatore di due password
   */
  public PasswordMatcherAdapter(BCryptPasswordEncoder encoderConfig) {
    this.encoderConfig = encoderConfig;
  }

  /**
   * Stabilisce se due password, una in chiaro e una cifrata, sono uguali
   *
   * @param rawPassword la password in chiaro
   * @param hashedPassword la password cifrata
   * @return true se sono uguali; false, altrimenti
   */
  @Override
  public boolean matches(String rawPassword, String hashedPassword) {
    return this.encoderConfig.matches(rawPassword, hashedPassword);
  }
}
