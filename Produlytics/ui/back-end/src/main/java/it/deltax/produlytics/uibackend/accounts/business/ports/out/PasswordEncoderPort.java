package it.deltax.produlytics.uibackend.accounts.business.ports.out;

/** La porta per la cifratura di una password in chiaro. */
public interface PasswordEncoderPort {
  String encode(String rawPassword);
}
