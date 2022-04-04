package it.deltax.produlytics.uibackend.accounts.business.domain;

public record AccountToInsert(
	String username,
	String password,
	boolean administrator
) {
}
