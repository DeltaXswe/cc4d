package it.deltax.produlytics.uibackend.admins.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import java.util.Optional;

/** La porta per cercare un utente dato l'username, per conto di un amministratore. */
public interface FindAccountByAdminPort {
  Optional<Account> findByUsername(String username);
}
