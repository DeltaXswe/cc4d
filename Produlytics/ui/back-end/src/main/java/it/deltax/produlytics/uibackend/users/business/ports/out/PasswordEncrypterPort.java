package it.deltax.produlytics.uibackend.users.business.ports.out;

public interface PasswordEncrypterPort {
    String encrypt(String plainText);
    boolean matches(String plainText, String hashedPassword);
}