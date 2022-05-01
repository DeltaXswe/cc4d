package it.deltax.produlytics.uibackend.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/** Interfaccia che modella il caso d'uso dell'aggiornamento della password di un utente. */
public interface UpdateAccountPasswordUseCase {
  void updatePasswordByUsername(AccountPasswordToUpdate accountToUpdate) throws BusinessException;
}
