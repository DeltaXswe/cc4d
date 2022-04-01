package it.deltax.produlytics.uibackend.admins.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordMatcherAdapter implements PasswordMatcherPort {
	BCryptPasswordEncoder encoder;

	PasswordMatcherAdapter(BCryptPasswordEncoder encoder){this.encoder = encoder; }

	@Override
	public boolean matches(String rawPassword, String hashedPassword) {
		return encoder.matches(rawPassword, hashedPassword);
	}
}
