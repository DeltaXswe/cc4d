package it.deltax.produlytics.uibackend.admins.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;

/** La porta per l'aggiornamento di un utente per mano di un amministratore. */
public interface UpdateAccountByAdminPort {
  void updateAccount(Account account);
}
