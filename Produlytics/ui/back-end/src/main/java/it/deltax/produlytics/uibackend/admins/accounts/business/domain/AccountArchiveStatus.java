package it.deltax.produlytics.uibackend.admins.accounts.business.domain;

/**
 * Record che rappresenta un utente a cui è stato modificato lo stato di archiviazione
 */
public record AccountArchiveStatus(
	String username,
	boolean archived
) {
}
