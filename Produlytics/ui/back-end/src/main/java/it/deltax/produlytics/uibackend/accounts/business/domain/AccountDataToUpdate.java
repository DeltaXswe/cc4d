package it.deltax.produlytics.uibackend.accounts.business.domain;

import java.util.Optional;

/**
 * Record che rappresenta i dati da aggiornare all'utente, modificati da un amministratore.
 */
public record AccountDataToUpdate(
    Optional<String> password,
    boolean administrator
) {}
