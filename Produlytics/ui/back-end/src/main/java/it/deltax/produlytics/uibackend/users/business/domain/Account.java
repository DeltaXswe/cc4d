package it.deltax.produlytics.uibackend.users.business.domain;

public record Account(
        String username,
        String hashedPassword,
		boolean admin,
		boolean archived
) {
}
