package it.deltax.produlytics.uibackend.accounts.business.domain;

public record AccountTiny(
	String username,
	boolean administrator,
	boolean archived
) {
}
