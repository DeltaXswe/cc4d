package it.deltax.produlytics.api.exceptions;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Questa classe si occupa di catturare e gestire le eccezioni lanciate dal controller e produrre un
 * adeguato messaggio di errore per il chiamante.
 */
@ControllerAdvice
@SuppressWarnings("unused")
public class BusinessExceptionHandler {
  /**
   * Questo metodo viene chiamato quando il controller lancia un'eccezione. Una volta ricevuta, si
   * occupa di convertirla in un messaggio di errore per il client, con annesso codice di stato
   * HTTP.
   *
   * @param businessException L'eccezione lanciata dal controller.
   * @return La risposta da inviare al client.
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Map<String, String>> handleStatusException(
      BusinessException businessException) {
    Map<String, String> body = Map.of("errorCode", businessException.getCode());

    HttpStatus httpStatus = switch (businessException.getType()) {
      case AUTHENTICATION -> HttpStatus.UNAUTHORIZED; // 401
      case NOT_FOUND -> HttpStatus.NOT_FOUND; // 404
      case ARCHIVED -> HttpStatus.GONE; // 410
    };

    return new ResponseEntity<>(body, httpStatus);
  }
}
