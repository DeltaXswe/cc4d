package it.deltax.produlytics.uibackend.admins.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * L'interfaccia che rappresenta il caso d'uso di aggiornamento dello stato di archiviazione di un utente
 * @author Leila Dardouri
 */
public interface UpdateAccountArchiveStatusUseCase {
	void updateAccountArchiveStatus(AccountArchiveStatus accountArchiveStatus) throws BusinessException;
}
