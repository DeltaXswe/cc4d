package it.deltax.produlytics.uibackend.users.business.ports.in;

public interface ChangeUserPasswordUseCase {
    void changeByUsername(String username, String currentPassword, String hashedPassword);
}