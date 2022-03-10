package it.deltax.produlytics.uibackend.exceptions.advices;

import it.deltax.produlytics.uibackend.exceptions.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResourceNotFoundException.ResourceNotFoundInfo resourceNotFoundHandler(ResourceNotFoundException rex) {
        return rex.getInfo();
    }
}
