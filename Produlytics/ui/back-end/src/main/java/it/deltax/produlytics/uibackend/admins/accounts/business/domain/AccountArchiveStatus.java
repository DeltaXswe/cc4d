package it.deltax.produlytics.uibackend.admins.accounts.business.domain;

/**
 * Il record rappresenta un utente a cui è stato modificato lo stato di archiviazione
 * @author Leila Dardouri
 */
public record AccountArchiveStatus(
	String username,
	boolean archived
) {
}
