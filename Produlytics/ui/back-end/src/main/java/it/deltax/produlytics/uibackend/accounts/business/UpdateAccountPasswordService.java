package it.deltax.produlytics.uibackend.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.EncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccountPasswordService implements UpdateAccountPasswordUseCase {
    private final UpdateAccountPasswordPort updateAccountPasswordPort;
    private final FindAccountPort findAccountPort;
    private final EncoderPort encoderPort;

    public UpdateAccountPasswordService(
            UpdateAccountPasswordPort updateAccountPasswordPort,
            @Qualifier("accountAdapter") FindAccountPort findAccountPort,
            EncoderPort encoderPort){
        this.updateAccountPasswordPort = updateAccountPasswordPort;
        this.findAccountPort = findAccountPort;
        this.encoderPort = encoderPort;
    }

    @Override
    public void updatePasswordByUsername(AccountPasswordToUpdate command) throws BusinessException {
        if(command.newPassword().length() < 6)
            throw new BusinessException("invalidNewPassword", ErrorType.GENERIC);

        Account.AccountBuilder toUpdate = findAccountPort.findByUsername(command.username())
            .map(account -> account.toBuilder())
            .orElseThrow(() -> new BusinessException(("accountNotFound"), ErrorType.NOT_FOUND));

        if (encoderPort.matches(command.currentPassword(),toUpdate.build().hashedPassword())) {
            String hashedNewPassword = encoderPort.encode(command.newPassword());
            toUpdate.withHashedPassword(hashedNewPassword);
            updateAccountPasswordPort.updateAccountPassword(toUpdate.build());
        } else {
            throw new BusinessException("wrongCurrentPassword", ErrorType.AUTHENTICATION);
        }
    }
}