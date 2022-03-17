package it.deltax.produlytics.uibackend.users.business.ports.in;

public interface ChangeUserPasswordUseCase {
    boolean changeByUsername(String username, String currentPassword, String hashedPassword);
}