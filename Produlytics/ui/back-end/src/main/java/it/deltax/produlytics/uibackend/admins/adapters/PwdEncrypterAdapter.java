package it.deltax.produlytics.uibackend.admins.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PwdEncrypterPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PwdEncrypterAdapter implements PwdEncrypterPort {
	BCryptPasswordEncoder encoder;

	PwdEncrypterAdapter(BCryptPasswordEncoder encoder){this.encoder = encoder; }

	@Override
	public String encrypt(String plainText){
		return encoder.encode(plainText);
	}
}
