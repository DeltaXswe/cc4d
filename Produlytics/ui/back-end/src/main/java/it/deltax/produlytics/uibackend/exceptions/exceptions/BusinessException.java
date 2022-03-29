package it.deltax.produlytics.uibackend.exceptions.exceptions;

import it.deltax.produlytics.uibackend.exceptions.ErrorType;

public class BusinessException extends Exception {
private final ErrorType type;
	public BusinessException(String code, ErrorType type) {
		super(code);
		this.type = type;
	}

	public ErrorType getType() {
		return type;
	}
}
