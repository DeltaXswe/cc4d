package it.deltax.produlytics.uibackend.admins.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

public interface UpdateAccountByAdminPort {
	void updateAccount(Account account);
}