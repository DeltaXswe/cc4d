package it.deltax.produlytics.uibackend.admins.accounts.business.domain;

/**
 * Record che rappresenta un utente che deve essere inserito
 */
public record AccountToInsert(
	String username,
	String password,
	boolean administrator
) {
}
