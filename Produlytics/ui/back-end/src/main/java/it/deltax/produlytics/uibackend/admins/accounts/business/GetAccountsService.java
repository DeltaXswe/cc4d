package it.deltax.produlytics.uibackend.admins.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.GetAccountsPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.GetAccountsUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAccountsService implements GetAccountsUseCase {
	GetAccountsPort getAccountsPort;

	public GetAccountsService(GetAccountsPort getAccountsPort){
		this.getAccountsPort = getAccountsPort;
	}

	@Override
	public List<AccountTiny> getAccounts() {
		return getAccountsPort.getAccounts();
	}
}
