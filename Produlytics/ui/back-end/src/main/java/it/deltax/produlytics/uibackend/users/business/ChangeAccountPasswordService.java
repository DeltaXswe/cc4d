package it.deltax.produlytics.uibackend.users.business;

import it.deltax.produlytics.uibackend.users.business.domain.Account;
import it.deltax.produlytics.uibackend.users.business.ports.in.ChangeAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.users.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.users.business.ports.out.PasswordEncrypterPort;
import it.deltax.produlytics.uibackend.users.business.ports.out.UpdateAccountPort;

import java.util.Optional;

public class ChangeAccountPasswordService implements ChangeAccountPasswordUseCase {
    private final UpdateAccountPort updateUserPort;
    private final FindAccountPort findUserPort;
    private final PasswordEncrypterPort passwordEncrypter;

    public ChangeAccountPasswordService(
            UpdateAccountPort updateUserPort,
            FindAccountPort findUserPort,
            PasswordEncrypterPort passwordEncrypter){
        this.updateUserPort = updateUserPort;
        this.findUserPort = findUserPort;
        this.passwordEncrypter = passwordEncrypter;
    }

    @Override
    public boolean changeByUsername(String username, String currentPassword, String newPassword){
        // prima cerco l'utente
        Optional<Account> result = findUserPort.findByUsername(username);
        if(result.isPresent()){ //se non è null
            Account user = result.get();
            if(passwordEncrypter.matches(currentPassword, user.hashedPassword())){
                String encryptedNew = passwordEncrypter.encrypt(newPassword);
                updateUserPort.updateAccount(username, encryptedNew);
                return true;
            }
        }
        return false;
    }
}