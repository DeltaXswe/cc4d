package it.deltax.produlytics.uibackend.admins.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;

import java.util.List;

/**
 * La porta per l'ottenimento degli utenti
 * @author Leila Dardouri
 */
public interface GetAccountsPort {
	List<AccountTiny> getAccounts();
}