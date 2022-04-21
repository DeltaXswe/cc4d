package it.deltax.produlytics.api.exceptions;

/**
 * Questa classe rappresenta un'eccezione derivante dalle componenti di business.
 * Rappresenta un errore causato dall'esterno, e quindi gestibile.
 */
public class BusinessException extends Exception {
	/**
	 * Il codice identificativo dell'errore.
	 */
	private final String code;
	/**
	 * Il tipo o categoria di errore.
	 */
	private final ErrorType type;

	/**
	 * Crea una nuova istanza di `BusinessException`.
	 *
	 * @param code Il valore per il campo `code`.
	 * @param type Il valore per il campo `type`.
	 */
	public BusinessException(String code, ErrorType type) {
		this.code = code;
		this.type = type;
	}

	/**
	 * Getter per il campo `code`.
	 *
	 * @return il codice di errore.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Getter per il campo `type`.
	 *
	 * @return il tipo di errore.
	 */
	public ErrorType getType() {
		return type;
	}
}
