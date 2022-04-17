package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.domain.TinyAccount;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.GetAccountsService;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetAccountsPort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test di unità della classe GetAccountsService
 */
public class GetAccountsServiceTest {

	/**
	 * Testa il caso in cui ci sono degli utenti
	 */
	@Test
	void testGetAccounts()  {
		GetAccountsService service = new GetAccountsService(new GetNoAccountsPortMock());
		service.getAccounts();
	}

	// CLASSI MOCK
	static class GetAccountsPortMock implements GetAccountsPort {
		@Override
		public List<TinyAccount> getAccounts() {
			TinyAccount account1 = new TinyAccount("user1", false, false);
			TinyAccount account2 = new TinyAccount("user2", false, false);
			return new ArrayList<TinyAccount>(Arrays.asList(account1, account2));
		}
	}

	static class GetNoAccountsPortMock implements GetAccountsPort{
		@Override
		public List<TinyAccount> getAccounts() {
			return null;
		}
	}
}
