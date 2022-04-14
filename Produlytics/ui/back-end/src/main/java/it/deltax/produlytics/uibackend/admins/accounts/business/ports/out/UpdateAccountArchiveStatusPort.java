package it.deltax.produlytics.uibackend.admins.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

/**
 * La porta per l'aggiornamento dello stato di archiviazione di un utente
 */
public interface UpdateAccountArchiveStatusPort {
	void updateAccountArchiveStatus(Account account);
}
