package it.deltax.produlytics.uibackend.users.business.ports.out;

public interface UpdateAccountPort {
    boolean updateAccount(String username, String hashedPassword);
}