package smartosc.fresher.connectmysql.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExceptionResponse handle(EntityNotFoundException ex, WebRequest request) {
        return new ApiExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                Instant.now());
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiExceptionResponse handle(EntityExistsException ex, WebRequest request) {
        return new ApiExceptionResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                Instant.now());
    }
}
