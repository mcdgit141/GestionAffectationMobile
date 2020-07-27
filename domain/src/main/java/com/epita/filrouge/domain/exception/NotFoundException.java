package com.epita.filrouge.domain.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(String code, String message) {
        super(BusinessException.NOT_FOUND,message);

    }

}
