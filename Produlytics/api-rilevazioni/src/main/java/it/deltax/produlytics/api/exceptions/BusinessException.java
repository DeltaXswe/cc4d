package it.deltax.produlytics.api.exceptions;

// Contiene un errore gestibile lanciato dalla parte di business.
public class BusinessException extends Exception {
	// Il codice di errore
	private final String code;
	// Il tipo di errore, utile per poi decidere il codice di stato HTTP
	private final ErrorType type;

	public BusinessException(String code, ErrorType type) {
		this.code = code;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public ErrorType getType() {
		return type;
	}
}
