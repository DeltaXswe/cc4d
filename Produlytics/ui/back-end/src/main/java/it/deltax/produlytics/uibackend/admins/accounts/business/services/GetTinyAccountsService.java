package it.deltax.produlytics.uibackend.admins.accounts.business.services;

import it.deltax.produlytics.uibackend.accounts.business.domain.TinyAccount;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.GetTinyAccountsUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetTinyAccountsPort;
import java.util.List;

/** Il service per l'ottenimento degli utenti con le informazioni essenziali */
public class GetTinyAccountsService implements GetTinyAccountsUseCase {
  private final GetTinyAccountsPort getTinyAccountsPort;

  /**
   * Il costruttore
   *
   * @param getTinyAccountsPort la porta per ottenere gli utenti con le informazioni essenziali
   */
  public GetTinyAccountsService(GetTinyAccountsPort getTinyAccountsPort) {
    this.getTinyAccountsPort = getTinyAccountsPort;
  }

  /**
   * Restituisce gli utenti con le informazioni essenziali
   *
   * @return la lista degli utenti con le informazioni essenziali
   */
  @Override
  public List<TinyAccount> getTinyAccounts() {
    return this.getTinyAccountsPort.getTinyAccounts();
  }
}
