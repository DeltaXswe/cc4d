package it.deltax.produlytics.uibackend.accounts.business.services;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccountPasswordService implements UpdateAccountPasswordUseCase {
    private final FindAccountPort findAccountPort;
    private final PasswordMatcherPort passwordMatcherPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final UpdateAccountPasswordPort updateAccountPasswordPort;

    /**
     * Il costruttore
     * @param findAccountPort la porta per cercare un utente
     * @param passwordMatcherPort la porta per confrontare una password in chiaro con una cifrata
     * @param passwordEncoderPort la porta per cifrare una password
     * @param updateAccountPasswordPort la porta per aggiornare la password di un utente
     */
    public UpdateAccountPasswordService(
            @Qualifier("accountAdapter") FindAccountPort findAccountPort,
            PasswordMatcherPort passwordMatcherPort,
            PasswordEncoderPort passwordEncoderPort,
            UpdateAccountPasswordPort updateAccountPasswordPort
        ){
        this.findAccountPort = findAccountPort;
        this.passwordMatcherPort = passwordMatcherPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.updateAccountPasswordPort = updateAccountPasswordPort;
    }

    /**
     * Aggiorna la password dell'utente dato
     * @param accountToUpdate l'utente con username, password nuova e corrente
     * @throws BusinessException se la password non è valida o l'utente da aggiornare non è stato trovato
     */
    @Override
    public void updatePasswordByUsername(AccountPasswordToUpdate accountToUpdate) throws BusinessException {
        if (accountToUpdate.newPassword().length() < 6)
            throw new BusinessException("invalidNewPassword", ErrorType.GENERIC);

        Account.AccountBuilder toUpdate = this.findAccountPort.findByUsername(accountToUpdate.username())
            .map(account -> account.toBuilder())
            .orElseThrow(() -> new BusinessException(("accountNotFound"), ErrorType.NOT_FOUND));

        if (this.passwordMatcherPort.matches(accountToUpdate.currentPassword(),toUpdate.build().hashedPassword())) {
            String hashedNewPassword = this.passwordEncoderPort.encode(accountToUpdate.newPassword());
            toUpdate.withHashedPassword(hashedNewPassword);
            this.updateAccountPasswordPort.updateAccountPassword(toUpdate.build());
        } else {
            throw new BusinessException("wrongCurrentPassword", ErrorType.AUTHENTICATION);
        }
    }
}