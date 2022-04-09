package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatcherAdapter implements PasswordMatcherPort {
	@Autowired
	EncoderConfig encoderConfig;

	@Override
	public boolean matches(String rawPassword, String hashedPassword) {
		return encoderConfig.getEncoder().matches(rawPassword, hashedPassword);
	}
}
