package it.deltax.produlytics.uibackend.users.business.ports.in;

public interface ChangeAccountPasswordUseCase {
    boolean changeByUsername(String username, String currentPassword, String hashedPassword);
}