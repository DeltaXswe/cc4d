package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.EncoderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncoderAdapter implements EncoderPort {
    @Autowired
    final BCryptPasswordEncoder encoder;

    EncoderAdapter(BCryptPasswordEncoder encoder){this.encoder = encoder; }

    @Override
    public String encode(String plainPassword){
        return encoder.encode(plainPassword);
    }

    @Override
    public boolean matches(String plainText, String hashedPassword){
        return encoder.matches(plainText, hashedPassword);
    }
}