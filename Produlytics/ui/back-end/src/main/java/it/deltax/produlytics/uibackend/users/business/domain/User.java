package it.deltax.produlytics.uibackend.users.business.domain;

public record User(
        String username,
        boolean admin,
        String hashedPassword
) {
}
