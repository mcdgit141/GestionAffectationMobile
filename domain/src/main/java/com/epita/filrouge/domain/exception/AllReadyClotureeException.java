package com.epita.filrouge.domain.exception;

public class AllReadyClotureeException extends BusinessException{
    public AllReadyClotureeException(String message) {
        super(message, BusinessException.ALL_READY_CLOSED);
    }
}
