package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PwdEncrypterPort;
import it.deltax.produlytics.uibackend.admins.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.out.InsertAccountPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InsertAccountService implements InsertAccountUseCase {
	private final InsertAccountPort insertAccountPort;
	private final FindAccountPort findAccountPort;
	private final PwdEncrypterPort pwdEncrypterPort;

	private InsertAccountService(
		InsertAccountPort insertAccountPort,
		FindAccountPort findAccountPort,
		PwdEncrypterPort pwdEncrypterPort
	){
		this.insertAccountPort = insertAccountPort;
		this.findAccountPort = findAccountPort;
		this.pwdEncrypterPort = pwdEncrypterPort;
	}

	@Override
	public boolean insertAccount(String username, String password, boolean administrator){
		Optional<Account> result = findAccountPort.findByUsername(username);
		if(result.isPresent())//se esiste
			return false; //utente già esistente, errore

		String hashedPassword = pwdEncrypterPort.encrypt(password);
		insertAccountPort.insertAccount(username, hashedPassword, administrator);
		return true;
	}



}
