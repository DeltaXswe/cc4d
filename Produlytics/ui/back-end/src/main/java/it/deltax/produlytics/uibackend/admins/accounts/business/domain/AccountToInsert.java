package it.deltax.produlytics.uibackend.admins.accounts.business.domain;

public record AccountToInsert(
	String username,
	String password,
	boolean administrator
) {
}
