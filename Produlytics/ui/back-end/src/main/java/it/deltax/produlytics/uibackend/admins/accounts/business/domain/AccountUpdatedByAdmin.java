package it.deltax.produlytics.uibackend.admins.accounts.business.domain;

import java.util.Optional;

/**
 * Il record rappresenta un utente che è stato aggiornato da un amministratore
 * @author Leila Dardouri
 */
public record AccountUpdatedByAdmin(
	String username,
	Optional<String> newPassword,
	boolean administrator
) {
}
