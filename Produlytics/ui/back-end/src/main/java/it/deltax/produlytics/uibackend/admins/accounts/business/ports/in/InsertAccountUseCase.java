package it.deltax.produlytics.uibackend.admins.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountToInsert;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Interfaccia che rappresenta il caso d'uso di inserimento di un utente
 */
public interface InsertAccountUseCase {
	void insertAccount(AccountToInsert account) throws BusinessException;
}
