package it.deltax.produlytics.uibackend.accounts.business.domain;

import java.util.Optional;

public record DataToUpdate(
	Optional<String> newPassword,
	boolean administrator
) {}
