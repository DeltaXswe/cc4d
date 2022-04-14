package it.deltax.produlytics.uibackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configurazione di Spring Security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Bean
	AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(this.customUserDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());

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
			.antMatchers("/")
			.permitAll()
			.antMatchers("/admin/**")
			.hasAuthority(ProdulyticsGrantedAuthority.ADMIN.getAuthority())
			.antMatchers("/**")
			.hasAuthority(ProdulyticsGrantedAuthority.ACCOUNT.getAuthority())
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.and()
			.rememberMe().key(encoder.encode("produlytics"))
			.userDetailsService(customUserDetailsService);
	}
}
