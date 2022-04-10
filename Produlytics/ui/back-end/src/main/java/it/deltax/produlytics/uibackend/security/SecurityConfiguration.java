package it.deltax.produlytics.uibackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Bean
	AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(this.userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());

		return provider;
	}


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
			.formLogin()
			.loginProcessingUrl("/login")
			.and()
			.rememberMe().key(encoder.encode("produlytics")).rememberMeParameter("rememberMe")
		;
	}
}
