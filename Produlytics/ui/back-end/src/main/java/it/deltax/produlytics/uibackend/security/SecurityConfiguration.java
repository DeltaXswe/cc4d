package it.deltax.produlytics.uibackend.security;

import it.deltax.produlytics.uibackend.accounts.EncoderConfig;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configurazione di Spring Security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final UserDetailsAdapter userDetailsAdapter;
	private final EncoderConfig encoder;

	/**
	 * Il costruttore
	 * @param userDetailsAdapter il service per verificare che l'utente esista
	 * @param encoder il cifratore
	 */
	public SecurityConfiguration(UserDetailsAdapter userDetailsAdapter, EncoderConfig encoder) {
		this.userDetailsAdapter = userDetailsAdapter;
		this.encoder = encoder;
	}

	/**
	 * Crea un'istanza di AuthenticationProvider
	 * @param findAccountUseCase interfaccia rappresentante il caso d'uso per la ricerca di un utente, da passare al
	 *                           costruttore di UserDetailsAdapter
	 * @return la nuova istanza di DaoAuthenticationProvider
	 */
	@Bean
	public AuthenticationProvider authenticationProvider(FindAccountUseCase findAccountUseCase){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(new UserDetailsAdapter(findAccountUseCase));
		provider.setPasswordEncoder(encoder.getEncoder());

		return provider;
	}

	/**
	 * Configura Spring Security
	 * @param http la configurazione per richieste http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/admin/**")
			.hasAuthority(ProdulyticsGrantedAuthority.ADMIN.getAuthority())
			.antMatchers("/devices", "/accounts", "/logout")
			.hasAuthority(ProdulyticsGrantedAuthority.ACCOUNT.getAuthority())
			.antMatchers("/index.html", "/*", "/login")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.and()
			.rememberMe().key(encoder.getEncoder().encode("produlytics"))
			.userDetailsService(userDetailsAdapter)
			.and()
			.csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}
