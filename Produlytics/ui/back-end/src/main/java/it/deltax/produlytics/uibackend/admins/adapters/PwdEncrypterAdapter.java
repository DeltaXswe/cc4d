package it.deltax.produlytics.uibackend.admins.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PwdEncrypterAdapter implements PasswordEncoderPort {
	BCryptPasswordEncoder encoder;

	PwdEncrypterAdapter(BCryptPasswordEncoder encoder){this.encoder = encoder; }

	@Override
	public String encode(String plainPassword){
		return encoder.encode(plainPassword);
	}
}
