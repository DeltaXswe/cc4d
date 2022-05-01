package it.deltax.produlytics.uibackend.admins.accounts.business.services;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountByAdminPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;

/** Il service per l'aggiornamento di un utente per mano di un amministratore. */
public class UpdateAccountByAdminService implements UpdateAccountByAdminUseCase {
  private final FindAccountByAdminPort findAccountByAdminPort;
  private final PasswordEncoderPort passwordEncoderPort;
  private final UpdateAccountByAdminPort updateAccountByAdminPort;

  /**
   * Il costruttore.
   *
   * @param findAccountByAdminPort la porta per trovare un utente
   * @param passwordEncoderPort la porta per cifrare una password
   * @param updateAccountByAdminPort la porta usata da un amministratore per aggiornare un utente
   */
  public UpdateAccountByAdminService(
      FindAccountByAdminPort findAccountByAdminPort,
      PasswordEncoderPort passwordEncoderPort,
      UpdateAccountByAdminPort updateAccountByAdminPort) {
    this.findAccountByAdminPort = findAccountByAdminPort;
    this.passwordEncoderPort = passwordEncoderPort;
    this.updateAccountByAdminPort = updateAccountByAdminPort;
  }

  /**
   * Aggiorna l'utente dato, per conto di un amministratore.
   *
   * @param updatedAccount l'utente con permessi e, opzionalmente, password aggiornati
   * @throws BusinessException se la password non è valida o l'utente non è stato trovato
   */
  @Override
  public void updateByUsername(AccountUpdatedByAdmin updatedAccount) throws BusinessException {
    if (updatedAccount.newPassword().isPresent()
        && updatedAccount.newPassword().get().length() < 6) {
      throw new BusinessException("invalidNewPassword", ErrorType.GENERIC);
    }

    Account.AccountBuilder toUpdate =
        this.findAccountByAdminPort
            .findByUsername(updatedAccount.username())
            .map(account -> account.toBuilder())
            .orElseThrow(() -> new BusinessException("accountNotFound", ErrorType.NOT_FOUND));

    if (updatedAccount.newPassword().isPresent()) {
      String hashedPassword = this.passwordEncoderPort.encode(updatedAccount.newPassword().get());
      toUpdate.withHashedPassword(hashedPassword);
    }

    toUpdate.withAdministrator(updatedAccount.administrator());
    this.updateAccountByAdminPort.updateAccount(toUpdate.build());
  }
}
