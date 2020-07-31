package com.epita.filrouge.expositions.exception;

import com.epita.filrouge.domain.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionConversion extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionConversion.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(final NotFoundException ex) {

        final ErreurDTO error = new ErreurDTO();
        error.setMessage(ex.getMessage());
        error.setCode(ex.getCode());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedExceptionHandler(final AccessDeniedException ex) {

        final ErreurDTO error = new ErreurDTO();
        error.setMessage(ex.getMessage());

        LOG.error(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultHandler(final Exception ex) {

        final ErreurDTO error = new ErreurDTO();
        error.setMessage(ex.getMessage());
        error.setCode("99999");

        LOG.error(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
