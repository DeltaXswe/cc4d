package it.deltax.produlytics.uibackend.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

public interface UpdateAccountPasswordPort {
    void updateAccountPassword(Account account);
}