package it.deltax.produlytics.uibackend.admins.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateAccountArchiveStatusUseCase {
	void updateAccountArchiveStatus(AccountArchiveStatus accountArchiveStatus) throws BusinessException;
}
