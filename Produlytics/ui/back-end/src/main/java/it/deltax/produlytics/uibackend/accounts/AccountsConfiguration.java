package it.deltax.produlytics.uibackend.accounts;

import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.*;
import it.deltax.produlytics.uibackend.accounts.business.services.FindAccountService;
import it.deltax.produlytics.uibackend.accounts.business.services.UpdateAccountPasswordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountsConfiguration {
	@Bean
	FindAccountUseCase findAccountUseCase(@Qualifier("accountAdapter") FindAccountPort findAccountPort){
		return new FindAccountService(findAccountPort);
	}

	@Bean
	UpdateAccountPasswordUseCase updateAccountPasswordUseCase(
	@Qualifier("accountAdapter") FindAccountPort findAccountPort,
	PasswordMatcherPort passwordMatcherPort,
	PasswordEncoderPort passwordEncoderPort,
	UpdateAccountPasswordPort updateAccountPasswordPort){
		return new UpdateAccountPasswordService(
			findAccountPort, passwordMatcherPort, passwordEncoderPort, updateAccountPasswordPort);
	}

}
