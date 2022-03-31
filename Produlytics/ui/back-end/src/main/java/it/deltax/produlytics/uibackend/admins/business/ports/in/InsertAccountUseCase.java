package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.admins.business.domain.InsertAccount;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface InsertAccountUseCase {
	void insertAccount(InsertAccount account) throws BusinessException;
}