package com.epita.filrouge.domain.exception;

public class NotFoundException extends BusinessException {

      public NotFoundException(String message) {
            super(message, BusinessException.NOT_FOUND);
        }
    }



