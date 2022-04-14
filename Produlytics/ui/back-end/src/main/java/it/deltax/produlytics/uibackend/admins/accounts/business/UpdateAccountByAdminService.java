package it.deltax.produlytics.uibackend.admins.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Il service per l'aggiornamento di un utente per mano di un amministratore
 */
@Service
public class UpdateAccountByAdminService implements UpdateAccountByAdminUseCase {
	private final FindAccountPort findAccountPort;
	private final PasswordEncoderPort passwordEncoderPort;
	private final UpdateAccountByAdminPort updateAccountByAdminPort;


	/**
	 * Il costruttore
	 * @param findAccountPort la porta per trovare un utente
	 * @param passwordEncoderPort la porta per cifrare una password
	 * @param updateAccountByAdminPort la porta usata da un amministratore per aggiornare un utente
	 */
	public UpdateAccountByAdminService(
		FindAccountPort findAccountPort,
		@Qualifier("passwordEncoderAdapter") PasswordEncoderPort passwordEncoderPort,
		UpdateAccountByAdminPort updateAccountByAdminPort){
		this.findAccountPort = findAccountPort;
		this.passwordEncoderPort = passwordEncoderPort;
		this.updateAccountByAdminPort = updateAccountByAdminPort;
	}


	/**
	 * Aggiorna l'utente dato, per conto di un amministratore
	 * @param updatedAccount l'utente con permessi e, opzionalmente, password aggiornati
	 * @throws BusinessException se la password non è valida o l'utente non è stato trovato
	 */
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