package it.deltax.produlytics.uibackend.accounts.business.domain;

import lombok.Builder;

/** Record che rappresenta un utente completo di tutte le sue informazioni. */
public record Account(
    String username,
    String hashedPassword,
    boolean administrator,
    boolean archived
) {
  @Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
  public Account {}
}

