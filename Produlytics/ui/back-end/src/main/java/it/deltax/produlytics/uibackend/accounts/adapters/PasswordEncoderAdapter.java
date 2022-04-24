package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * L'adapter della classe BCryptPasswordEncoder per cifrare una password
 */
@Component
public class PasswordEncoderAdapter implements PasswordEncoderPort {
	private final BCryptPasswordEncoder encoderConfig;


	/**
	 * Il costruttore
	 * @param encoderConfig il cifratore
	 */
	public PasswordEncoderAdapter(BCryptPasswordEncoder encoderConfig) {
		this.encoderConfig = encoderConfig;
	}


	/**
	 * Cifra la password in chiaro data
	 * @param rawPassword la password in chiaro
	 * @return la password cifrata
	 */
	@Override
	public String encode(String rawPassword){
		return this.encoderConfig.encode(rawPassword);
	}
}
