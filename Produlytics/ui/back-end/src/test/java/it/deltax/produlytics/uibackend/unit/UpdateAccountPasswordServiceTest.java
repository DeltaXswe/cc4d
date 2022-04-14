package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.UpdateAccountPasswordService;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Test di unità della classe UpdateAccountPasswordService
 * @author Leila Dardouri
 */
public class UpdateAccountPasswordServiceTest {
	@Test
	void testNewPasswordNotValid() throws BusinessException {
		AccountPasswordToUpdate account = new AccountPasswordToUpdate(
			"user", "passwordvecchia", "p");
		UpdateAccountPasswordService service = new UpdateAccountPasswordService(
			new FindAccountPortMock(),
			new PasswordMatcherPortMock(),
			new PasswordEncoderPortMock(),
			new UpdateAccountPasswordPortMock()
		);
		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.updatePasswordByUsername(account));
		assert exception.getMessage().equals("invalidNewPassword");
		assert exception.getType() == ErrorType.GENERIC;
	}

	@Test
	void testWrongCurrentPassword() throws BusinessException {
		AccountPasswordToUpdate account = new AccountPasswordToUpdate(
			"user", "passwordvecchia", "passwordnuova");
		UpdateAccountPasswordService service = new UpdateAccountPasswordService(
			new FindAccountPortMock(),
			new PasswordMatcherPortMock(),
			new PasswordEncoderPortMock(),
			new UpdateAccountPasswordPortMock()
		);
		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.updatePasswordByUsername(account));
		assert exception.getMessage().equals("wrongCurrentPassword");
		assert exception.getType() == ErrorType.AUTHENTICATION;
	}

	@Test
	void testAccountNotFound() throws BusinessException {
		AccountPasswordToUpdate account = new AccountPasswordToUpdate(
			"user", "passwordvecchia", "passwordnuova");
		UpdateAccountPasswordService service = new UpdateAccountPasswordService(
			new FindAccountNotFoundPortMock(),
			new PasswordMatcherPortMock(),
			new PasswordEncoderPortMock(),
			new UpdateAccountPasswordPortMock()
		);

		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.updatePasswordByUsername(account));
		assert exception.getMessage().equals("accountNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	@Test
	void testOk() throws BusinessException {
		AccountPasswordToUpdate account = new AccountPasswordToUpdate(
			"user", "passwordvecchia", "passwordnuova");
		UpdateAccountPasswordService service = new UpdateAccountPasswordService(
			new FindAccountPortMock(),
			new PasswordMatcherPortTrueMock(),
			new PasswordEncoderPortMock(),
			new UpdateAccountPasswordPortMock()
		);
	}

	static class UpdateAccountPasswordPortMock implements UpdateAccountPasswordPort{
		@Override
		public void updateAccountPassword(Account account) {}
	}

	static class FindAccountPortMock implements FindAccountPort{
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

	static class PasswordMatcherPortMock implements PasswordMatcherPort{
		@Override
		public boolean matches(String rawPassword, String hashedPassword) {
			return false;
		}
	}

	static class PasswordMatcherPortTrueMock implements PasswordMatcherPort{
		@Override
		public boolean matches(String rawPassword, String hashedPassword) {
			return true;
		}
	}

	static class PasswordEncoderPortMock implements PasswordEncoderPort{
		@Override
		public String encode(String rawPassword) {
			return null;
		}
	}

}
