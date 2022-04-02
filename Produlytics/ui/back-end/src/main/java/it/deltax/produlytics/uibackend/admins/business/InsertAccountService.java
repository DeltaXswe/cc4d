package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.business.domain.InsertAccount;
import it.deltax.produlytics.uibackend.admins.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InsertAccountService implements InsertAccountUseCase {
	private final InsertAccountPort insertAccountPort;
	private final FindAccountPort findAccountPort;
	private final PasswordEncoderPort passwordEncoderPort;

	public InsertAccountService(
		InsertAccountPort insertAccountPort,
		FindAccountPort findAccountPort,
		@Qualifier("passwordEncoderAdapter") PasswordEncoderPort passwordEncoderPort
	){
		this.insertAccountPort = insertAccountPort;
		this.findAccountPort = findAccountPort;
		this.passwordEncoderPort = passwordEncoderPort;
	}

	@Override
	public void insertAccount(InsertAccount command) throws BusinessException {
		if(command.password().length() < 6)
			throw new BusinessException("invalidPassword", ErrorType.GENERIC);

		Optional<Account> result = findAccountPort.findByUsername(command.username());
		if(result.isPresent())
			throw new BusinessException("duplicateUsername", ErrorType.GENERIC);

		String hashedPassword = passwordEncoderPort.encode(command.password());
		insertAccountPort.insertAccount(new Account(command.username(),hashedPassword,command.administrator(),false));
	}



}
