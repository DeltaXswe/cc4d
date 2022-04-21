package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.GetTinyAccountsUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetTinyAccountsPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.GetTinyAccountsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsAdapterConfiguration {
	@Bean
	UserDetailsService userDetailsService(
		FindAccountUseCase findAccountUseCase){
		return new UserDetailsAdapter(findAccountUseCase);
	}
}
