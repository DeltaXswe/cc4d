package it.deltax.produlytics.uibackend.admins.accounts.business.ports.out;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;

/**
 * La porta per la codifica e il confronto di due password
 */
public interface EncoderPort extends PasswordEncoderPort, PasswordMatcherPort {}