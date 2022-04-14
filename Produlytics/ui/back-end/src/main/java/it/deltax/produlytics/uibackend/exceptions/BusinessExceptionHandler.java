package it.deltax.produlytics.uibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

/**
 * Cattura le eccezioni e ritorna un messaggio di errore appropiato
 */
@ControllerAdvice
public class BusinessExceptionHandler {
	/**
	 * Gestisce l'eccezione catturata e mappa il tipo di errore con il suo corrispondente stato HTTP
	 * @param exception l'eccezione da gestire
	 * @return il messaggio di errore e lo stato HTTP
	 */
	@ExceptionHandler(BusinessException.class)
	ResponseEntity<HashMap<String, String>> handleStatusException(BusinessException exception) {
		var error = new HashMap<String, String>();
		error.put("errorCode", exception.getMessage());

		HttpStatus httpStatus = switch(exception.getType()) {
			case GENERIC -> HttpStatus.BAD_REQUEST;
			case AUTHENTICATION -> HttpStatus.UNAUTHORIZED;
			case NOT_FOUND -> HttpStatus.NOT_FOUND;
		};

		return new ResponseEntity<>(error, httpStatus);
	}
}
