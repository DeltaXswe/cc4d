package it.deltax.produlytics.uibackend.security;

import org.springframework.security.core.GrantedAuthority;

/** Enumerazione che rappresenta i vari gradi di autorizzazioni esistenti */
public enum ProdulyticsGrantedAuthority implements GrantedAuthority {
  ADMIN,
  ACCOUNT;

  /**
   * Restituisce il grado di autorizzazione
   *
   * @return una stringa rappresentante il grado di autorizzazione
   */
  @Override
  public String getAuthority() {
    return String.valueOf(this);
  }
}
