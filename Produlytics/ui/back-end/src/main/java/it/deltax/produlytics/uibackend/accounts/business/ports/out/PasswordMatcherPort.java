package it.deltax.produlytics.uibackend.accounts.business.ports.out;

/** La porta per il confronto di due password: una in chiaro e una cifrata */
public interface PasswordMatcherPort {
  boolean matches(String rawPassword, String hashedPassword);
}
