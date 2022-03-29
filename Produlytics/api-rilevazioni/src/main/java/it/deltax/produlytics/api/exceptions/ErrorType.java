package it.deltax.produlytics.api.exceptions;

// Tipo di errore della parte business. È pensato per mappare a un codice di stato HTTP
public enum ErrorType {
	AUTHENTICATION, // 401
	NOT_FOUND, // 404
	ARCHIVED // 410
}
