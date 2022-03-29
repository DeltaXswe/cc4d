package it.deltax.produlytics.uibackend.accounts.business.domain;

public record Account(
        String username,
        String hashedPassword,
		boolean administrator,
		boolean archived
) {
}
