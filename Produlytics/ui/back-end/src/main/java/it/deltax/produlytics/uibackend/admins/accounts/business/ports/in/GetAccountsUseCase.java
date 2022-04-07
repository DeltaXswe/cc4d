package it.deltax.produlytics.uibackend.admins.accounts.business.ports.in;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;

import java.util.List;

public interface GetAccountsUseCase {
	List<AccountTiny> getAccounts();
}
