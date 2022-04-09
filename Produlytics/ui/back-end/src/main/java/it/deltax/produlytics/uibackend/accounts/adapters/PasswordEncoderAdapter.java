package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PasswordEncoderAdapter implements PasswordEncoderPort {
	@Autowired
	EncoderConfig encoderConfig;

	@Override
	public String encode(String rawPassword){
		return encoderConfig.getEncoder().encode(rawPassword);
	}
}
