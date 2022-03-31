package it.deltax.produlytics.uibackend.accounts.business.ports.out;

public interface PasswordEncoderPort {
	String encode(String plainPassword);
}
