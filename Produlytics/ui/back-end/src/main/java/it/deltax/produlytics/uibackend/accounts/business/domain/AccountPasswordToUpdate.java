package it.deltax.produlytics.uibackend.accounts.business.domain;

public record AccountPasswordToUpdate(
	String username,
	String currentPassword,
	String newPassword
) {
}
