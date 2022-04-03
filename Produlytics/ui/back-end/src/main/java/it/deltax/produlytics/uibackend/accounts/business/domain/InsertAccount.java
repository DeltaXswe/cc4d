package it.deltax.produlytics.uibackend.accounts.business.domain;

public record InsertAccount(
	String username,
	String password,
	boolean administrator
) {
}
