package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * L'adapter della classe BCryptPasswordEncoder per cifrare una password
 * @author Leila Dardouri
 */
@Component
public class PasswordEncoderAdapter implements PasswordEncoderPort {
	@Autowired
	private final EncoderConfig encoderConfig;


	/**
	 * Il costruttore
	 * @param encoderConfig il cifratore
	 */
	public PasswordEncoderAdapter(EncoderConfig encoderConfig) {
		this.encoderConfig = encoderConfig;
	}


	/**
	 * Cifra la password in chiaro data
	 * @param rawPassword la password in chiaro
	 * @return la password cifrata
	 */
	@Override
	public String encode(String rawPassword){
		return this.encoderConfig.getEncoder().encode(rawPassword);
	}
}
