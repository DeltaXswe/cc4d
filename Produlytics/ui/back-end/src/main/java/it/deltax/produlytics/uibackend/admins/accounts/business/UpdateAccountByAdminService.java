package it.deltax.produlytics.uibackend.admins.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccountByAdminService implements UpdateAccountByAdminUseCase {
	private final FindAccountPort findAccountPort;
	private final PasswordEncoderPort passwordEncoderPort;
	private final UpdateAccountByAdminPort updateAccountByAdminPort;

	public UpdateAccountByAdminService(
		FindAccountPort findUserPort,
		@Qualifier("passwordEncoderAdapter") PasswordEncoderPort passwordEncoderPort,
		UpdateAccountByAdminPort updateAccountByAdminPort){
		this.findAccountPort = findUserPort;
		this.passwordEncoderPort = passwordEncoderPort;
		this.updateAccountByAdminPort = updateAccountByAdminPort;
	}

	@Override
	public void updateByUsername(AccountUpdatedByAdmin updatedAccount) throws BusinessException {
		if (updatedAccount.newPassword().isPresent() && updatedAccount.newPassword().get().length() < 6)
			throw new BusinessException("invalidNewPassword", ErrorType.GENERIC);

		Account.AccountBuilder toUpdate = this.findAccountPort.findByUsername(updatedAccount.username())
			.map(account -> account.toBuilder())
				.orElseThrow(() -> new BusinessException("accountNotFound", ErrorType.NOT_FOUND));

		if (updatedAccount.newPassword().isPresent()) {
			String hashedPassword = this.passwordEncoderPort.encode(updatedAccount.newPassword().get());
			toUpdate.withHashedPassword(hashedPassword);
		}

		toUpdate.withAdministrator(updatedAccount.administrator());
		this.updateAccountByAdminPort.updateAccount(toUpdate.build());
	}
}