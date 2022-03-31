package it.deltax.produlytics.uibackend.accounts.business.domain;

import lombok.Builder;

public record Account(
	String username,
	String hashedPassword,
	boolean administrator,
	boolean archived
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public Account{}

	/*
	public static AccountBuilder builder(String username, String hashedPassword){
		return new Account(username, hashedPassword, false, false).toBuilder();
	}
	*/


}

