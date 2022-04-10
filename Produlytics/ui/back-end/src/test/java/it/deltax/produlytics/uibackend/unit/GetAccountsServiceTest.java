package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;
import it.deltax.produlytics.uibackend.admins.accounts.business.GetAccountsService;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetAccountsPort;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test di unità della classe GetAccountsService
 * @author Leila Dardouri
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

	/**
	 * Testa il caso in cui non ci sono degli utenti
	 */
	@Test
	void testGetNoAccounts()  {
		GetAccountsService service = new GetAccountsService(new GetNoAccountsPortMock());
		service.getAccounts();
	}

	// CLASSI MOCK
	static class GetAccountsPortMock implements GetAccountsPort {
		@Override
		public List<AccountTiny> getAccounts() {
			AccountTiny account1 = new AccountTiny("user1", false, false);
			AccountTiny account2 = new AccountTiny("user2", false, false);
			return new ArrayList<AccountTiny>(Arrays.asList(account1, account2));
		}
	}

	static class GetNoAccountsPortMock implements GetAccountsPort{
		@Override
		public List<AccountTiny> getAccounts() {
			return null;
		}
	}
}
