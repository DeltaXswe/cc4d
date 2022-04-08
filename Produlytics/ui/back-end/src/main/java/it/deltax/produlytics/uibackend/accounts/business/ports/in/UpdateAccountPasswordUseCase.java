package it.deltax.produlytics.uibackend.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateAccountPasswordUseCase {
    void updatePasswordByUsername(AccountPasswordToUpdate accountToUpdate) throws BusinessException;
}