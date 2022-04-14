package it.deltax.produlytics.uibackend.exceptions;

/**
 * Contiene un errore gestibile lanciato dalla parte di business
 */
public class BusinessException extends Exception {
	private final ErrorType type;

	/**
	 * Il costruttore
	 * @param code il messaggio di errore
	 * @param type il tipo di errore, utile per poi decidere il codice di stato HTTP
	 */
	public BusinessException(String code, ErrorType type) {
		super(code);
		this.type = type;
	}

	/**
	 * Getter del tipo di errore
	 * @return il tipo di errore dell'eccezione
	 */
	public ErrorType getType() {
		return type;
	}
}
