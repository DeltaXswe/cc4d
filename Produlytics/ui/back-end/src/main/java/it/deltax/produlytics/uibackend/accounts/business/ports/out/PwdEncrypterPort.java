package it.deltax.produlytics.uibackend.accounts.business.ports.out;

public interface PwdEncrypterPort {
	String encrypt(String plainText);
}
