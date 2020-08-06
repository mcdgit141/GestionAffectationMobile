package com.epita.filrouge.domain.exception;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message) {
        super(message,BusinessException.BAD_REQUEST);
    }
}
