package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.InsertAccountService;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountToInsert;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class InsertAccountServiceTest {

	@Test
	void testInvalidPassword() throws BusinessException {
		AccountToInsert account = new AccountToInsert("user", "p", false);
		InsertAccountService service = new InsertAccountService(
			new FindAccountPortMock(), new PasswordEncoderPortMock(), new InsertAccountPortMock()
		);
		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.insertAccount(account));
		assert exception.getMessage().equals("invalidPassword");
		assert exception.getType() == ErrorType.GENERIC;
	}

	@Test
	void testDuplicateUsername() throws BusinessException {
		AccountToInsert account = new AccountToInsert("user", "password", false);
		InsertAccountService service = new InsertAccountService(
			new FindAccountPortMock(), new PasswordEncoderPortMock(), new InsertAccountPortMock()
		);
		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.insertAccount(account));
		assert exception.getMessage().equals("duplicateUsername");
		assert exception.getType() == ErrorType.GENERIC;
	}

	@Test
	void testOk() throws BusinessException {
		AccountToInsert account = new AccountToInsert("user1", "password", false);
		InsertAccountService service = new InsertAccountService(
			new FindAccountPortMock(), new PasswordEncoderPortMock(), new InsertAccountPortMock()
		);
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


	static class InsertAccountPortMock implements InsertAccountPort{

		@Override
		public void insertAccount(Account account) {

		}
	}
}
