package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.UpdateAccountArchiveStatusService;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountArchiveStatusPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Test di unità della classe UpdateAccountArchiveStatusService
 */
public class UpdateAccountArchiveStatusServiceTest {
	/**
	 * Testa il caso in cui l'utente non sia stato trovato
	 * @throws BusinessException l'utente non è stato trovato
	 */
	@Test
	void testAccountNotFound() throws BusinessException {
		AccountArchiveStatus account = new AccountArchiveStatus("user", false);
		UpdateAccountArchiveStatusService service = new UpdateAccountArchiveStatusService(
			new FindAccountNotFoundPortMock(), new UpdateAccountArchiveStatusPortMock()
		);
		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.updateAccountArchiveStatus(account));
		assert exception.getMessage().equals("accountNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	/**
	 * Testa il caso in cui lo stato di archiviazione viene aggiornato correttamente
	 * @throws BusinessException l'utente non è stato trovato
	 */
	@Test
	void testOk() throws BusinessException {
		AccountArchiveStatus account = new AccountArchiveStatus("user", false);
		UpdateAccountArchiveStatusService service = new UpdateAccountArchiveStatusService(
			new FindAccountPortMock(), new UpdateAccountArchiveStatusPortMock()
		);
		service.updateAccountArchiveStatus(account);
	}

	// CLASSI MOCK
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

	static class UpdateAccountArchiveStatusPortMock implements UpdateAccountArchiveStatusPort {
		@Override
		public void updateAccountArchiveStatus(Account account) {}
	}
}
