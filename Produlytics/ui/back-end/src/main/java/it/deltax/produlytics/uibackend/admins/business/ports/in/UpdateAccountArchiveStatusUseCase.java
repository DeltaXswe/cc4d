package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateAccountArchiveStatusUseCase {
	void updateAccountArchiveStatus(AccountArchiveStatus command) throws BusinessException;
}
