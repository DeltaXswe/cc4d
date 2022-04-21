package it.deltax.produlytics.uibackend.admins.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.TinyAccount;

import java.util.List;

/**
 * L'interfaccia che rappresenta il caso d'uso di ottenimento degli utenti
 */
public interface GetTinyAccountsUseCase {
	List<TinyAccount> getTinyAccounts();
}
