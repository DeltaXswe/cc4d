package it.deltax.produlytics.uibackend.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

public interface InsertAccountPort {
	void insertAccount(Account account);
}
