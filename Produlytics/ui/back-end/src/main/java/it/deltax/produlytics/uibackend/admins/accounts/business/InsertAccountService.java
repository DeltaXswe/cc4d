package it.deltax.produlytics.uibackend.admins.accounts.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountToInsert;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InsertAccountService implements InsertAccountUseCase {
	private final FindAccountPort findAccountPort;
	private final PasswordEncoderPort passwordEncoderPort;
	private final InsertAccountPort insertAccountPort;

	public InsertAccountService(
		FindAccountPort findAccountPort,
		@Qualifier("passwordEncoderAdapter") PasswordEncoderPort passwordEncoderPort,
		InsertAccountPort insertAccountPort
	){
		this.findAccountPort = findAccountPort;
		this.passwordEncoderPort = passwordEncoderPort;
		this.insertAccountPort = insertAccountPort;
	}

	@Override
	public void insertAccount(AccountToInsert account) throws BusinessException {
		if (account.password().length() < 6)
			throw new BusinessException("invalidPassword", ErrorType.GENERIC);

		Optional<Account> result = findAccountPort.findByUsername(account.username());
		if (result.isPresent())
			throw new BusinessException("duplicateUsername", ErrorType.GENERIC);

		String hashedPassword = passwordEncoderPort.encode(account.password());
		insertAccountPort.insertAccount(new Account(
			account.username(),hashedPassword,account.administrator(),false));
	}



}
