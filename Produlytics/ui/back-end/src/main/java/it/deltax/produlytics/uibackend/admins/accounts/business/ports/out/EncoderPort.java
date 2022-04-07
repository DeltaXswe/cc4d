package it.deltax.produlytics.uibackend.admins.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;

public interface EncoderPort extends PasswordEncoderPort, PasswordMatcherPort {}