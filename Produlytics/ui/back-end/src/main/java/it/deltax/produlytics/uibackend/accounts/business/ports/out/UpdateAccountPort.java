package it.deltax.produlytics.uibackend.accounts.business.ports.out;

public interface UpdateAccountPort {
    boolean updateAccount(String username, String hashedPassword);
}