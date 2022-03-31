package it.deltax.produlytics.uibackend.admins.business.domain;

public record InsertAccount(
	String username,
	String password,
	boolean administrator
) {
}
