package it.deltax.produlytics.uibackend.admins.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountToInsert;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * L'interfaccia che rappresenta il caso d'uso di inserimento di un utente
 * @author Leila Dardouri
 */
public interface InsertAccountUseCase {
	void insertAccount(AccountToInsert account) throws BusinessException;
}
