package it.deltax.produlytics.uibackend.admins.accounts.business.domain;

/**
 * Il record rappresenta un utente che deve essere inserito
 */
public record AccountToInsert(
	String username,
	String password,
	boolean administrator
) {
}
