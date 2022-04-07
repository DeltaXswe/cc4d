package it.deltax.produlytics.uibackend.accounts.business.domain;

import java.util.Optional;

public record AccountDataToUpdate(
	Optional<String> newPassword,
	boolean administrator
) {}
