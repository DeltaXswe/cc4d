package it.deltax.produlytics.uibackend.admins.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountToInsert;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface InsertAccountUseCase {
	void insertAccount(AccountToInsert account) throws BusinessException;
}
