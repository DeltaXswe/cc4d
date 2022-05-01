package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.FindAccountByAdminPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.UpdateAccountByAdminService;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/** Test di unità della classe UpdateAccountByAdminService */
public class UpdateAccountByAdminServiceTest {
  /**
   * Testa il caso in cui la password non è valida
   *
   * @throws BusinessException la password non è valida o l'utente non è stato trovato
   */
  @Test
  void testInvalidNewPassword() throws BusinessException {
    AccountUpdatedByAdmin account = new AccountUpdatedByAdmin("user", Optional.of("p"), false);
    UpdateAccountByAdminService service =
        new UpdateAccountByAdminService(
            new FindAccountByAdminPortMock(),
            new PasswordEncoderPortMock(),
            new UpdateAccountByAdminPortMock());
    BusinessException exception =
        assertThrows(BusinessException.class, () -> service.updateByUsername(account));
    assert exception.getCode().equals("invalidNewPassword");
    assert exception.getType() == ErrorType.GENERIC;
  }

  /**
   * Testa il caso in cui l'utente non sia stato trovato
   *
   * @throws BusinessException la password non è valida o l'utente non è stato trovato
   */
  @Test
  void testAccountNotFound() throws BusinessException {
    AccountUpdatedByAdmin account =
        new AccountUpdatedByAdmin("user", Optional.of("passwordnuova"), false);
    UpdateAccountByAdminService service =
        new UpdateAccountByAdminService(
            new FindAccountByAdminNotFoundPortMock(),
            new PasswordEncoderPortMock(),
            new UpdateAccountByAdminPortMock());
    BusinessException exception =
        assertThrows(BusinessException.class, () -> service.updateByUsername(account));
    assert exception.getCode().equals("accountNotFound");
    assert exception.getType() == ErrorType.NOT_FOUND;
  }

  /**
   * Testa il caso in cui vengono aggiornati password e permessi
   *
   * @throws BusinessException la password non è valida o l'utente non è stato trovato
   */
  @Test
  void testUpdatePasswordAndPermissions() throws BusinessException {
    AccountUpdatedByAdmin account =
        new AccountUpdatedByAdmin("user", Optional.of("passwordnuova"), false);
    UpdateAccountByAdminService service =
        new UpdateAccountByAdminService(
            new FindAccountByAdminPortMock(),
            new PasswordEncoderPortMock(),
            new UpdateAccountByAdminPortMock());
    service.updateByUsername(account);
  }

  /**
   * Testa il caso in cui vengono aggiornati solo i permessi
   *
   * @throws BusinessException la password non è valida o l'utente non è stato trovato
   */
  @Test
  void testUpdatePermissions() throws BusinessException {
    AccountUpdatedByAdmin account = new AccountUpdatedByAdmin("user", Optional.empty(), false);
    UpdateAccountByAdminService service =
        new UpdateAccountByAdminService(
            new FindAccountByAdminPortMock(),
            new PasswordEncoderPortMock(),
            new UpdateAccountByAdminPortMock());
    service.updateByUsername(account);
  }

  // CLASSI MOCK
  static class FindAccountByAdminPortMock implements FindAccountByAdminPort {
    @Override
    public Optional<Account> findByUsername(String username) {
      return Optional.of(new Account("user", "passwordvecchia", false, false));
    }
  }

  static class FindAccountByAdminNotFoundPortMock implements FindAccountByAdminPort {
    @Override
    public Optional<Account> findByUsername(String username) {
      return Optional.empty();
    }
  }

  static class PasswordEncoderPortMock implements PasswordEncoderPort {
    @Override
    public String encode(String rawPassword) {
      return null;
    }
  }

  static class UpdateAccountByAdminPortMock implements UpdateAccountByAdminPort {
    @Override
    public void updateAccount(Account account) {}
  }
}
