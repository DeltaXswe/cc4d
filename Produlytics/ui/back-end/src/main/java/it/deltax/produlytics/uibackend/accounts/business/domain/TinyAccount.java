package it.deltax.produlytics.uibackend.accounts.business.domain;

/**
 * Record che rappresenta l'intestazione di un utente, con l'username, i permessi e lo stato di
 * archiviazione.
 */
public record TinyAccount(
    String username,
    boolean administrator,
    boolean archived
) {
}
