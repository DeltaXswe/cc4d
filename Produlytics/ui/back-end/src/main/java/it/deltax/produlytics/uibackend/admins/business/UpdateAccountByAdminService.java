package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.admins.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccountByAdminService implements UpdateAccountByAdminUseCase {
	private final UpdateAccountByAdminPort updateAccountByAdminPort;
	private final FindAccountPort findAccountPort;
	private final PasswordEncoderPort passwordEncoderPort;

	public UpdateAccountByAdminService(
		UpdateAccountByAdminPort updateAccountByAdminPort,
		FindAccountPort findUserPort,
		@Qualifier("passwordEncoderAdapter") PasswordEncoderPort passwordEncoderPort){
		this.updateAccountByAdminPort = updateAccountByAdminPort;
		this.findAccountPort = findUserPort;
		this.passwordEncoderPort = passwordEncoderPort;
	}

	@Override
	public void updateByUsername(AccountUpdatedByAdmin command) throws BusinessException {
		if (command.newPassword().isPresent() && command.newPassword().get().length() < 6)
			throw new BusinessException("invalidNewPassword", ErrorType.GENERIC);

		Account.AccountBuilder toUpdate = findAccountPort.findByUsername(command.username())
			.map(account -> account.toBuilder())
				.orElseThrow(() -> new BusinessException("accountNotFound", ErrorType.NOT_FOUND));

		if(command.newPassword().isPresent()) {
			String hashedPassword = passwordEncoderPort.encode(command.newPassword().get());
			toUpdate.withHashedPassword(hashedPassword);
		}

		toUpdate.withAdministrator(command.administrator());
		updateAccountByAdminPort.updateAccount(toUpdate.build());
	}
}