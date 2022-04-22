package it.deltax.produlytics.uibackend.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.ChangeAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.EncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPort;

import java.util.Optional;

public class ChangeAccountPasswordService implements ChangeAccountPasswordUseCase {
    private final UpdateAccountPort updateAccountPort;
    private final FindAccountPort findUserPort;
    private final EncoderPort passwordEncrypter;

    public ChangeAccountPasswordService(
            UpdateAccountPort updateUserPort,
            FindAccountPort findUserPort,
            EncoderPort passwordEncrypter){
        this.updateAccountPort = updateUserPort;
        this.findUserPort = findUserPort;
        this.passwordEncrypter = passwordEncrypter;
    }

    @Override
    public boolean changeByUsername(String username, String currentPassword, String newPassword){
        // prima cerco l'utente
        Optional<Account> result = findUserPort.findByUsername(username);
        if(result.isPresent()){ //se non è null
            if(passwordEncrypter.matches(currentPassword, result.get().hashedPassword())){
                String encryptedNew = passwordEncrypter.encrypt(newPassword);
                return updateAccountPort.updateAccount(username, encryptedNew);
            }
        }
        return false;
    }
}