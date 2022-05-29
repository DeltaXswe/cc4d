package it.deltax.produlytics.api.exceptions;

import lombok.AllArgsConstructor;

/**
 * Questa classe rappresenta un'eccezione derivante dalle componenti di business. Rappresenta un
 * errore causato dall'esterno, e quindi gestibile.
 */
@AllArgsConstructor
public class BusinessException extends Exception {
  /** Il codice identificativo dell'errore. */
  private final String code;
  /** Il tipo o categoria di errore. */
  private final ErrorType type;

  /**
   * Getter per il campo {@code code}.
   *
   * @return il codice di errore
   */
  public String getCode() {
    return code;
  }

  /**
   * Getter per il campo {@code type}.
   *
   * @return il tipo di errore
   */
  public ErrorType getType() {
    return type;
  }
}
