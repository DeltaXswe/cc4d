package it.deltax.produlytics.uibackend.exceptions;

/**
 * Tipo di errore della parte business. È pensato per mappare a un codice di stato HTTP
 */
public enum ErrorType {
	GENERIC,		// 400
	AUTHENTICATION,	// 401
	NOT_FOUND		// 404
}
