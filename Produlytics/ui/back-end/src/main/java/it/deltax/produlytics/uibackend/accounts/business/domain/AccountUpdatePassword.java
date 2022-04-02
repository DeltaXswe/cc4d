package it.deltax.produlytics.uibackend.accounts.business.domain;

public record AccountUpdatePassword(
	String username,
	String currentPassword,
	String newPassword
) {
}
