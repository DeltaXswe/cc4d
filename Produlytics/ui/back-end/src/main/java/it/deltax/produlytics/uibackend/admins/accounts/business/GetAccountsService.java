package it.deltax.produlytics.uibackend.admins.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetAccountsPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.GetAccountsUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Il service per l'ottenimento degli utenti
 */
@Service
public class GetAccountsService implements GetAccountsUseCase {
	private final GetAccountsPort getAccountsPort;


	/**
	 * Il costruttore
	 * @param getAccountsPort la porta per ottenere gli utenti
	 */
	public GetAccountsService(GetAccountsPort getAccountsPort){
		this.getAccountsPort = getAccountsPort;
	}


	/**
	 * Restituisce gli utenti
	 * @return la lista degli utenti
	 */
	@Override
	public List<AccountTiny> getAccounts() {
		return this.getAccountsPort.getAccounts();
	}
}
