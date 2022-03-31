package it.deltax.produlytics.uibackend.admins.business.domain;

import java.util.Optional;

public record UpdateAdminAccout(
	String username,
	Optional<String> newPassword,
	boolean administrator
) {
}
