package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * L'adapter della classe BCryptPasswordEncoder per confrontare due password
 */
@Component
public class PasswordMatcherAdapter implements PasswordMatcherPort {
	private final EncoderConfig encoderConfig;


	/**
	 * Il costruttore
	 * @param encoderConfig il confrontatore di due password
	 */
	public PasswordMatcherAdapter(EncoderConfig encoderConfig) {
		this.encoderConfig = encoderConfig;
	}


	/**
	 * Stabilisce se due password, una in chiaro e una cifrata, sono uguali
	 * @param rawPassword la password in chiaro
	 * @param hashedPassword la password cifrata
	 * @return true se sono uguali; false, altrimenti
	 */
	@Override
	public boolean matches(String rawPassword, String hashedPassword) {
		return this.encoderConfig.getEncoder().matches(rawPassword, hashedPassword);
	}
}
