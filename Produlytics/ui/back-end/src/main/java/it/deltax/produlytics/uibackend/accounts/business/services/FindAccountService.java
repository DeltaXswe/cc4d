package it.deltax.produlytics.uibackend.accounts.business.services;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountByAdminPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.util.Optional;

/** Il service per l'ottenimento degli utenti. */
public class FindAccountService implements FindAccountUseCase {
  private final FindAccountByAdminPort findAccountPort;

  /**
   * Il costruttore.
   *
   * @param findAccountPort la cercare l'utente
   */
  public FindAccountService(FindAccountByAdminPort findAccountPort) {
    this.findAccountPort = findAccountPort;
  }

  /**
   * Cerca l'utente.
   *
   * @return l'utente se lo trova; Optional vuoto, altrimenti
   */
  @Override
  public Optional<Account> findByUsername(String username) throws BusinessException {
    return Optional.ofNullable(
        this.findAccountPort
            .findByUsername(username)
            .map(account -> account.toBuilder().build())
            .orElseThrow(() -> new BusinessException(("accountNotFound"), ErrorType.NOT_FOUND)));
  }
}
