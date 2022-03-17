package it.deltax.produlytics.uibackend.users.business.ports.in;

public interface PasswordEncrypter {
    String encrypt(String plainText);
    boolean matches(String plainText, String hash);
}