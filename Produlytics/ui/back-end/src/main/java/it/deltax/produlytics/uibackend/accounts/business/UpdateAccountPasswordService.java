package it.deltax.produlytics.uibackend.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccountPasswordService implements UpdateAccountPasswordUseCase {
    private final UpdateAccountPasswordPort updateAccountPasswordPort;
    private final FindAccountPort findAccountPort;
    private final PasswordMatcherPort passwordMatcherPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UpdateAccountPasswordService(
            UpdateAccountPasswordPort updateAccountPasswordPort,
            FindAccountPort findAccountPort,
            PasswordMatcherPort passwordMatcherPort,
            PasswordEncoderPort passwordEncoderPort
        ){
        this.updateAccountPasswordPort = updateAccountPasswordPort;
        this.findAccountPort = findAccountPort;
        this.passwordMatcherPort = passwordMatcherPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void updatePasswordByUsername(AccountPasswordToUpdate accountToUpdate) throws BusinessException {
        if (accountToUpdate.newPassword().length() < 6)
            throw new BusinessException("invalidNewPassword", ErrorType.GENERIC);

        Account.AccountBuilder toUpdate = this.findAccountPort.findByUsername(accountToUpdate.username())
            .map(account -> account.toBuilder())
            .orElseThrow(() -> new BusinessException(("accountNotFound"), ErrorType.NOT_FOUND));

        System.out.println(accountToUpdate.currentPassword());
        System.out.println(toUpdate.build().hashedPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.encode("passwordvecchia"));
        if (this.passwordMatcherPort.matches(accountToUpdate.currentPassword(),toUpdate.build().hashedPassword())) {
            String hashedNewPassword = this.passwordEncoderPort.encode(accountToUpdate.newPassword());
            toUpdate.withHashedPassword(hashedNewPassword);
            this.updateAccountPasswordPort.updateAccountPassword(toUpdate.build());
        } else {
            throw new BusinessException("wrongCurrentPassword", ErrorType.AUTHENTICATION);
        }
    }
}