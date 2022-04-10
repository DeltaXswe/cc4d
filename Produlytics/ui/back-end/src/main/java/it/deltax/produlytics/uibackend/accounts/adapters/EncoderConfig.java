package it.deltax.produlytics.uibackend.accounts.adapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Leila Dardouri
 */
@Configuration
public class EncoderConfig {
	@Bean
	public BCryptPasswordEncoder getEncoder(){
		return new BCryptPasswordEncoder();
	}
}