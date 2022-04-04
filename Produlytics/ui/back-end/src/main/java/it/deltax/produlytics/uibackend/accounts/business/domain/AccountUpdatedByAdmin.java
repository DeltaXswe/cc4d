package it.deltax.produlytics.uibackend.accounts.business.domain;

import java.util.Optional;

public record AccountUpdatedByAdmin(
	String username,
	Optional<String> newPassword,
	boolean administrator
) {
}
