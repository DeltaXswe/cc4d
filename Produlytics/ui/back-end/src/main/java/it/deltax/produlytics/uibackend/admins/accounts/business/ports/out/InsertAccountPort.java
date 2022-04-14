package it.deltax.produlytics.uibackend.admins.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

/**
 * La porta per l'inserimento di un utente
 */
public interface InsertAccountPort {
	void insertAccount(Account account);
}
