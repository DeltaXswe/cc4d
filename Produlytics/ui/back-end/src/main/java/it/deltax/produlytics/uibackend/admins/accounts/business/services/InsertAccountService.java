package it.deltax.produlytics.uibackend.admins.accounts.business.services;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountToInsert;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.util.Optional;

/** Il service per l'inserimento di un utente. */
public class InsertAccountService implements InsertAccountUseCase {
  private final FindAccountPort findAccountPort;
  private final PasswordEncoderPort passwordEncoderPort;
  private final InsertAccountPort insertAccountPort;

  /**
   * Il costruttore.
   *
   * @param findAccountPort la porta per trovare un utente
   * @param passwordEncoderPort la porta per cifrare una password
   * @param insertAccountPort la porta per memorizzare un utente
   */
  public InsertAccountService(
      FindAccountPort findAccountPort,
      PasswordEncoderPort passwordEncoderPort,
      InsertAccountPort insertAccountPort) {
    this.findAccountPort = findAccountPort;
    this.passwordEncoderPort = passwordEncoderPort;
    this.insertAccountPort = insertAccountPort;
  }

  /**
   * Memorizza l'utente dato.
   *
   * @param account l'utente da memorizzare
   * @throws BusinessException se la password dell'utente non è valida oppure esiste già l'username
   */
  @Override
  public void insertAccount(AccountToInsert account) throws BusinessException {
    if (account.password().length() < 6) {
      throw new BusinessException("invalidPassword", ErrorType.GENERIC);
    }

    Optional<Account> result = this.findAccountPort.findByUsername(account.username());
    if (result.isPresent()) {
      throw new BusinessException("duplicateUsername", ErrorType.GENERIC);
    }

    String hashedPassword = this.passwordEncoderPort.encode(account.password());
    this.insertAccountPort.insertAccount(
        new Account(account.username(), hashedPassword, account.administrator(), false));
  }
}
