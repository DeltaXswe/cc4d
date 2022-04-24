package it.deltax.produlytics.uibackend.admins.accounts.business.domain;

import java.util.Optional;

/**
 * Record che rappresenta un utente che è stato aggiornato da un amministratore
 */
public record AccountUpdatedByAdmin(
	String username,
	Optional<String> newPassword,
	boolean administrator
) {
}
