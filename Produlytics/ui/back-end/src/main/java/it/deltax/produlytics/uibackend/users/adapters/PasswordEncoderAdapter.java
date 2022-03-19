package it.deltax.produlytics.uibackend.users.adapters;

import it.deltax.produlytics.uibackend.users.business.ports.out.PasswordEncrypterPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderAdapter implements PasswordEncrypterPort {
    final BCryptPasswordEncoder encoder;

    PasswordEncoderAdapter(BCryptPasswordEncoder encoder){ this.encoder = encoder; }

    @Override
    public String encrypt(String plainText){
        return encoder.encode(plainText);
    }

    @Override
    public boolean matches(String plainText, String hashedPassword){
        return encoder.matches(plainText, hashedPassword);
    }
}