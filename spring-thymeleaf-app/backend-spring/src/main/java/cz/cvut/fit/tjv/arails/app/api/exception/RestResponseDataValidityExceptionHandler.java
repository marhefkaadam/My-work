package cz.cvut.fit.tjv.arails.app.api.exception;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseDataValidityExceptionHandler extends ResponseEntityExceptionHandler {
    protected String message;

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Violation of data validity.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleIncorrectIdGiven(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Entity with given ID doesn't exist.", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = PropertyValueException.class)
    public ResponseEntity<Object> handleIncorrectValueException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Not-null property references a null or transient value.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
