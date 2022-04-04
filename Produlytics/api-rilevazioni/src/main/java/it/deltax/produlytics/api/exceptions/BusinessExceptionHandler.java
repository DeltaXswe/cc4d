package it.deltax.produlytics.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

// Cattura le eccezioni e ritorna un messaggio di errore appropiato
@ControllerAdvice
@SuppressWarnings("unused")
public class BusinessExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Map<String, String>> handleStatusException(BusinessException e) {
		Map<String, String> body = Map.of("errorCode", e.getCode());

		HttpStatus httpStatus = switch(e.getType()) {
			case AUTHENTICATION -> HttpStatus.UNAUTHORIZED; // 401
			case NOT_FOUND -> HttpStatus.NOT_FOUND; // 404
			case ARCHIVED -> HttpStatus.GONE; // 410
		};

		return new ResponseEntity<>(body, httpStatus);
	}
}
