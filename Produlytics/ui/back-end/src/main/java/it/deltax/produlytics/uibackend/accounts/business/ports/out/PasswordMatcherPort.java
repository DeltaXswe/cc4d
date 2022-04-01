package it.deltax.produlytics.uibackend.accounts.business.ports.out;

public interface PasswordMatcherPort {
	boolean matches(String rawPassword, String hashedPassword);
}
