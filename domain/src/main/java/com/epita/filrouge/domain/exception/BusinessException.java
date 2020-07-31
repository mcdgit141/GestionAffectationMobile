package com.epita.filrouge.domain.exception;

public abstract class BusinessException extends RuntimeException{

    public static String ALREADY_EXIST_CODE = "ALREADYEXIST";

    private String code;

    public BusinessException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

}
