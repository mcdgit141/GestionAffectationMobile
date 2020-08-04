package com.epita.filrouge.expositions.exception;

import com.epita.filrouge.domain.exception.BusinessException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionConversion extends ResponseEntityExceptionHandler {

    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String CODE = "code";

    public ExceptionConversion(FooMapperExceptionCode mapperExceptionCode) {
        this.mapperExceptionCode = mapperExceptionCode;
    }

    @Autowired
    private final FooMapperExceptionCode mapperExceptionCode;

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<Object> handleValueInstantiationException(ValueInstantiationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST);
        body.put(ERROR, "Command is not valid");
        String message = ex.getLocalizedMessage();
        body.put(MESSAGE, message.substring(message.indexOf("problem:")+9, message.indexOf('\n')));
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
}

