package com.epita.filrouge.domain.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(final String code, final String message) {
        super(code, message);
    }

}
