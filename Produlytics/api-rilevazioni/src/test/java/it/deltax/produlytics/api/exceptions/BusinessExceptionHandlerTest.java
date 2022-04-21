package it.deltax.produlytics.api.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Objects;

public class BusinessExceptionHandlerTest {
  @Test
  void testAuthenticationType() {
    BusinessExceptionHandler exceptionHandler = new BusinessExceptionHandler();
    BusinessException exception = new BusinessException("blablabla", ErrorType.AUTHENTICATION);
    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleStatusException(exception);
    assert response.getStatusCode() == HttpStatus.UNAUTHORIZED;
    assert response.getStatusCodeValue() == 401;
    assert Objects.requireNonNull(response.getBody()).equals(Map.of("errorCode", "blablabla"));
  }

  @Test
  void testNotFoundType() {
    BusinessExceptionHandler exceptionHandler = new BusinessExceptionHandler();
    BusinessException exception = new BusinessException("bliblobla", ErrorType.NOT_FOUND);
    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleStatusException(exception);
    assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    assert response.getStatusCodeValue() == 404;
    assert Objects.requireNonNull(response.getBody()).equals(Map.of("errorCode", "bliblobla"));
  }

  @Test
  void testArchivedType() {
    BusinessExceptionHandler exceptionHandler = new BusinessExceptionHandler();
    BusinessException exception = new BusinessException("bubu", ErrorType.ARCHIVED);
    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleStatusException(exception);
    assert response.getStatusCode() == HttpStatus.GONE;
    assert response.getStatusCodeValue() == 410;
    assert Objects.requireNonNull(response.getBody()).equals(Map.of("errorCode", "bubu"));
  }
}
