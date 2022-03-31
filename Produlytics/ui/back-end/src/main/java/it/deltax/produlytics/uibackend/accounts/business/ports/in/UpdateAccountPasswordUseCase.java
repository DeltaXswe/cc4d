package it.deltax.produlytics.uibackend.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.UpdateAccountPassword;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateAccountPasswordUseCase {
    void updatePasswordByUsername(UpdateAccountPassword command) throws BusinessException;
}