package it.deltax.produlytics.uibackend.admins.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Interfaccia che rappresenta il caso d'uso di aggiornamento di un utente per mano di un
 * amministratore.
 */
public interface UpdateAccountByAdminUseCase {
  void updateByUsername(AccountUpdatedByAdmin updatedAccount) throws BusinessException;
}
