package com.epita.filrouge.domain.exception;

public abstract class BusinessException extends RuntimeException {

    public static final String NOT_FOUND = "BUSINESS_NOT_FOUND";
    public static final String ALL_READY_EXIST = "BUSINESS_ALL_READY_EXIST";
    public static final String ALL_READY_CLOSED = "BUSINESS_ALL_READY_CLOSED";
    public static final String BAD_REQUEST = "BAD REQUEST";


    private final String code;

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
