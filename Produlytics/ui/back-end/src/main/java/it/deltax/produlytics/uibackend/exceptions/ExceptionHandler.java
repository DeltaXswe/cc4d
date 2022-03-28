package it.deltax.produlytics.uibackend.exceptions;

import it.deltax.produlytics.uibackend.exceptions.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler()
	ResponseEntity<Error> handleStatusException(NotFoundException e) {
		return new ResponseEntity<Error>(new Error(e.getMessage()), HttpStatus.NOT_FOUND);
	}
}
