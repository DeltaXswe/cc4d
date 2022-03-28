package it.deltax.produlytics.uibackend.exceptions.exceptions;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String errorCode) {
		super(errorCode);
	}
}
