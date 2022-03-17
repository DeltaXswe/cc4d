package it.deltax.produlytics.uibackend.users.adapters;

import it.deltax.produlytics.uibackend.users.business.ports.in.PasswordEncrypter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderAdapter implements PasswordEncrypter {
    final BCryptPasswordEncoder encoder;

    PasswordEncoderAdapter(BCryptPasswordEncoder encoder){ this.encoder = encoder; }

    @Override
    private String encrypt(String plainText){
        return encoder.encode(plainText);
    }

    @Override
    private boolean matches(String plainText, String hash){
        return encoder.matches(plainText, hash);
    }
}