package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.domain.TinyAccount;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetTinyAccountsPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.GetTinyAccountsService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Test di unità della classe GetAccountsService */
public class GetAccountsServiceTest {

  /** Testa il caso in cui ci sono degli utenti */
  @Test
  void testGetAccounts() {
    GetTinyAccountsService service = new GetTinyAccountsService(new GetNoAccountsPortMock());
    service.getTinyAccounts();
  }

  // CLASSI MOCK
  static class GetAccountsPortMock implements GetTinyAccountsPort {
    @Override
    public List<TinyAccount> getTinyAccounts() {
      TinyAccount account1 = new TinyAccount("user1", false, false);
      TinyAccount account2 = new TinyAccount("user2", false, false);
      return new ArrayList<TinyAccount>(Arrays.asList(account1, account2));
    }
  }

  static class GetNoAccountsPortMock implements GetTinyAccountsPort {
    @Override
    public List<TinyAccount> getTinyAccounts() {
      return null;
    }
  }
}
