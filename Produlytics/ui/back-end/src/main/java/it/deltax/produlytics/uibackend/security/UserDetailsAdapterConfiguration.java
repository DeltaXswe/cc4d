package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsAdapterConfiguration {
	@Bean
	UserDetailsService userDetailsService(FindAccountUseCase findAccountUseCase){
		return new UserDetailsAdapter(findAccountUseCase);
	}
}
