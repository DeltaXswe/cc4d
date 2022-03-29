package it.deltax.produlytics.uibackend.exceptions;

import it.deltax.produlytics.uibackend.exceptions.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
	ResponseEntity<?> handleStatusException(NotFoundException e) {
		var Error = new HashMap<String, String>();
		Error.put("errorCode", e.getMessage());

		return new ResponseEntity<>(Error, HttpStatus.NOT_FOUND);
	}
}
