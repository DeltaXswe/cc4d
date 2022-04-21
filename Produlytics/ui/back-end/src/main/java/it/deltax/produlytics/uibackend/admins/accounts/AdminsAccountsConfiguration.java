package it.deltax.produlytics.uibackend.admins.accounts;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.GetAccountsUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetAccountsPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountArchiveStatusPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.GetAccountsService;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.InsertAccountService;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.UpdateAccountArchiveStatusService;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.UpdateAccountByAdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminsAccountsConfiguration {
	@Bean
	GetAccountsUseCase getAccountsUseCase(GetAccountsPort getAccountsPort){
		return new GetAccountsService(getAccountsPort);
	}

	@Bean
	InsertAccountUseCase insertAccountUseCase(
		@Qualifier("adminAccountAdapter") FindAccountPort findAccountPort,
		PasswordEncoderPort passwordEncoderPort,
		InsertAccountPort insertAccountPort){
		return new InsertAccountService(findAccountPort, passwordEncoderPort, insertAccountPort);
	}

	@Bean
	UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase(
		@Qualifier("adminAccountAdapter") FindAccountPort findAccountPort,
		UpdateAccountArchiveStatusPort updateAccountArchiveStatusPort){
		return new UpdateAccountArchiveStatusService(findAccountPort, updateAccountArchiveStatusPort);
	}

	@Bean
	UpdateAccountByAdminUseCase updateAccountByAdminUseCase(
		@Qualifier("adminAccountAdapter") FindAccountPort findAccountPort,
		PasswordEncoderPort passwordEncoderPort,
		UpdateAccountByAdminPort updateAccountByAdminPort){
		return new UpdateAccountByAdminService(findAccountPort, passwordEncoderPort, updateAccountByAdminPort);
	}


}
