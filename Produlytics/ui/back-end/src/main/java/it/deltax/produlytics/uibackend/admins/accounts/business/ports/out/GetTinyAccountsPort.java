package it.deltax.produlytics.uibackend.admins.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.domain.TinyAccount;
import java.util.List;

/** La porta per l'ottenimento degli utenti con le informazioni essenziali */
public interface GetTinyAccountsPort {
  List<TinyAccount> getTinyAccounts();
}
