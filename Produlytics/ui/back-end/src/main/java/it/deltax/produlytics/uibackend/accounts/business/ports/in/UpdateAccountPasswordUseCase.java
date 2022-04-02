package it.deltax.produlytics.uibackend.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountUpdatePassword;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface UpdateAccountPasswordUseCase {
    void updatePasswordByUsername(AccountUpdatePassword command) throws BusinessException;
}