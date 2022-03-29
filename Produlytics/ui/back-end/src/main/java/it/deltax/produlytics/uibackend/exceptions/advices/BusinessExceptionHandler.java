package it.deltax.produlytics.uibackend.exceptions.advices;

import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;

@ControllerAdvice
public class BusinessExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
	ResponseEntity<?> handleStatusException(BusinessException e) {
		var error = new HashMap<String, String>();
		error.put("errorCode", e.getMessage());

		HttpStatus httpStatus = switch(e.getType()) {
			case GENERIC -> HttpStatus.BAD_REQUEST;
			case AUTHENTICATION -> HttpStatus.UNAUTHORIZED;
			case NOT_FOUND -> HttpStatus.NOT_FOUND;
		};

		return new ResponseEntity<>(error, httpStatus);
	}
}
