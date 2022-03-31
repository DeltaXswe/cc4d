package it.deltax.produlytics.uibackend.accounts.business.ports.out;

public interface UpdateAccountPort {
    void updateAccount(String username, String hashedPassword);
}