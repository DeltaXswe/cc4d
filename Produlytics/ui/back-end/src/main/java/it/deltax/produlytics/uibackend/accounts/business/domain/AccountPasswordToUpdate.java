package it.deltax.produlytics.uibackend.accounts.business.domain;

/**
 * Record che rappresenta un utente con le informazioni per la modifica della propria password.
 */
public record AccountPasswordToUpdate(
    String username,
    String currentPassword,
    String newPassword
) {
}
