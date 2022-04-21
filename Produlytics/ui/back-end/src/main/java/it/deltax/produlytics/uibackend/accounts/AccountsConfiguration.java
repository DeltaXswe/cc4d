package it.deltax.produlytics.uibackend.accounts;

import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.accounts.business.services.UpdateAccountPasswordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountsConfiguration {
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
