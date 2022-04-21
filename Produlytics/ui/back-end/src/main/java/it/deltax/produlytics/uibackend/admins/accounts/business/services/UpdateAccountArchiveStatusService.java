package it.deltax.produlytics.uibackend.admins.accounts.business.services;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountArchiveStatusPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Il service per l'aggiornamento dello stato di archiviazione di un utente
 */
public class UpdateAccountArchiveStatusService implements UpdateAccountArchiveStatusUseCase {
	private final FindAccountPort findAccountPort;
	private final UpdateAccountArchiveStatusPort updateAccountArchiveStatusPort;


	/**
	 * Il costruttore
	 * @param findAccountPort la porta per cercare un utente
	 * @param updateAccountArchiveStatusPort la porta per aggiornare lo stato di archiviazione di un utente
	 */
	public UpdateAccountArchiveStatusService(
		FindAccountPort findAccountPort,
		UpdateAccountArchiveStatusPort updateAccountArchiveStatusPort) {
		this.findAccountPort = findAccountPort;
		this.updateAccountArchiveStatusPort = updateAccountArchiveStatusPort;
	}


	/**
	 * Aggiorna lo stato di archiviazione dell'utente dato
	 * @param accountArchiveStatus l'utente con lo stato di archiviazione aggiornato
	 * @throws BusinessException se l'utente non è stato trovato
	 */
	@Override
	public void updateAccountArchiveStatus(AccountArchiveStatus accountArchiveStatus) throws BusinessException {
		Account.AccountBuilder toUpdate = this.findAccountPort.findByUsername(
			accountArchiveStatus.username())
			.map(account -> account.toBuilder())
			.orElseThrow(() -> new BusinessException("accountNotFound", ErrorType.NOT_FOUND));

		toUpdate.withArchived(accountArchiveStatus.archived());
		this.updateAccountArchiveStatusPort.updateAccountArchiveStatus(toUpdate.build());
	}
}
