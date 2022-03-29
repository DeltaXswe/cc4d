package it.deltax.produlytics.uibackend.accounts.business.ports.out;

public interface PwdMatcherPort {
	boolean matches(String plainText, String hashedPassword);
}
