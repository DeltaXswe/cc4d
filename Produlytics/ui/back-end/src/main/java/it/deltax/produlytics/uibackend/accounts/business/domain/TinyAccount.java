package it.deltax.produlytics.uibackend.accounts.business.domain;

public record TinyAccount(
	String username,
	boolean administrator,
	boolean archived
) {
}
