package it.deltax.produlytics.uibackend.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.UpdateAccountPassword;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.EncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccountPasswordService implements UpdateAccountPasswordUseCase {
    private final UpdateAccountPort updateAccountPort;
    private final FindAccountPort findAccountPort;
    private final EncoderPort encoderPort;

    public UpdateAccountPasswordService(
            UpdateAccountPort updateUserPort,
            @Qualifier("accountAdapter") FindAccountPort findAccountPort,
            EncoderPort encoderPort){
        this.updateAccountPort = updateUserPort;
        this.findAccountPort = findAccountPort;
        this.encoderPort = encoderPort;
    }

    @Override
    public void updatePasswordByUsername(UpdateAccountPassword command) throws BusinessException {
        if(command.newPassword().length() < 6)
            throw new BusinessException("invalidNewPassword", ErrorType.GENERIC);

        Account toUpdate = findAccountPort.findByUsername(command.username())
            .map(account -> new Account(account.username(),
                            account.hashedPassword(),
                            account.administrator(),
                            account.archived()))
            .orElseThrow(() -> new BusinessException(("accountNotFound"), ErrorType.NOT_FOUND));

        if (encoderPort.matches(command.currentPassword(),toUpdate.hashedPassword())) {
            String hashedNewPassword = encoderPort.encode(command.newPassword());
            updateAccountPort.updateAccount(command.username(), hashedNewPassword);
        } else {
            throw new BusinessException("wrongCurrentPassword", ErrorType.AUTHENTICATION);
        }
    }
}