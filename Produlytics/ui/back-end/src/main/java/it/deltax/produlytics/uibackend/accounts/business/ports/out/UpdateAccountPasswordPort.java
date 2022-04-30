package it.deltax.produlytics.uibackend.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

/** La porta per l'aggiornamento della password di un utente */
public interface UpdateAccountPasswordPort {
  void updateAccountPassword(Account account);
}
