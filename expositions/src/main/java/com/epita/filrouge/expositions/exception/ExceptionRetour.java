package com.epita.filrouge.expositions.exception;

import com.epita.filrouge.domain.exception.BadRequestException;
import com.epita.filrouge.domain.exception.BusinessException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionRetour extends ResponseEntityExceptionHandler {

    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String CODE = "code";

    public ExceptionRetour() {
    }

    @Autowired
    private MapperExceptionCode mapperExceptionCode;

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<Object> handleValueInstantiationException(ValueInstantiationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST);
        body.put(ERROR, "Command is not valid");
        String message = ex.getLocalizedMessage();
        body.put(MESSAGE, message.substring(message.indexOf("problem:") + 9, message.indexOf('\n')));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                MethodArgumentNotValidException ex, HttpHeaders headers,
                HttpStatus status, WebRequest request) {

            Map<String, Object> body = new LinkedHashMap<>();
            body.put(TIMESTAMP, LocalDate.now());
            body.put(STATUS, status.value());

            List<String> errors = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.toList());

            body.put(ERROR, errors);

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(BusinessException businessException, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(CODE, businessException.getCode());
        body.put(ERROR, "Business logic error");
        body.put(MESSAGE, businessException.getLocalizedMessage());
        return new ResponseEntity<>(body, mapperExceptionCode.mapCodeToHTTPCode(businessException.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknowException(Exception ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        body.put(ERROR, "Unknow Exception");
        body.put(MESSAGE, ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedExceptionHandler(AccessDeniedException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.FORBIDDEN);
        body.put(ERROR, "Acces interdit");
        body.put(MESSAGE, ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}

