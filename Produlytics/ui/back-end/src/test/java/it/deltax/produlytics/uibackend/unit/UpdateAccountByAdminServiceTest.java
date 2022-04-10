package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.UpdateAccountByAdminService;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UpdateAccountByAdminServiceTest {

	@Test
	void testInvalidNewPassword() throws BusinessException{
		AccountUpdatedByAdmin account = new AccountUpdatedByAdmin(
			"user", Optional.of("p"), false);
		UpdateAccountByAdminService service = new UpdateAccountByAdminService(
			new FindAccountPortMock(),
			new PasswordEncoderPortMock(),
			new UpdateAccountByAdminPortMock()
		);
		BusinessException exception = assertThrows(
			BusinessException.class, () -> service.updateByUsername(account));
		assert exception.getMessage().equals("invalidNewPassword");
		assert exception.getType() == ErrorType.GENERIC;
	}

	@Test
	void testAccountNotFound() throws BusinessException{
		AccountUpdatedByAdmin account = new AccountUpdatedByAdmin(
			"user", Optional.of("passwordnuova"), false);
		UpdateAccountByAdminService service = new UpdateAccountByAdminService(
			new FindAccountNotFoundPortMock(),
			new PasswordEncoderPortMock(),
			new UpdateAccountByAdminPortMock()
		);
		BusinessException exception = assertThrows(
			BusinessException.class, () -> service.updateByUsername(account));
		assert exception.getMessage().equals("accountNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	@Test
	void testUpdatePasswordAndPermissions() throws BusinessException{
		AccountUpdatedByAdmin account = new AccountUpdatedByAdmin(
			"user", Optional.of("passwordnuova"), false);
		UpdateAccountByAdminService service = new UpdateAccountByAdminService(
			new FindAccountPortMock(),
			new PasswordEncoderPortMock(),
			new UpdateAccountByAdminPortMock()
		);
		service.updateByUsername(account);
	}

	@Test
	void testUpdatePermissions() throws BusinessException{
		AccountUpdatedByAdmin account = new AccountUpdatedByAdmin(
			"user", Optional.empty(), false);
		UpdateAccountByAdminService service = new UpdateAccountByAdminService(
			new FindAccountPortMock(),
			new PasswordEncoderPortMock(),
			new UpdateAccountByAdminPortMock()
		);
		service.updateByUsername(account);
	}

	static class FindAccountPortMock implements FindAccountPort {
		@Override
		public Optional<Account> findByUsername(String username) {
			return Optional.of(
				new Account("user", "passwordvecchia", false, false));
		}
	}

	static class FindAccountNotFoundPortMock implements FindAccountPort{
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
		public void updateAccount(Account account) {

		}
	}

}
