package it.deltax.produlytics.uibackend.accounts.business.domain;

public record UpdateAccountPassword(
	String username,
	String currentPassword,
	String newPassword
) {}
