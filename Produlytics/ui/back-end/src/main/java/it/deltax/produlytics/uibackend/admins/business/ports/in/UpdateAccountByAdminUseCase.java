package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateAccountByAdminUseCase {
	void updateByUsername(AccountUpdatedByAdmin command) throws BusinessException;
}
