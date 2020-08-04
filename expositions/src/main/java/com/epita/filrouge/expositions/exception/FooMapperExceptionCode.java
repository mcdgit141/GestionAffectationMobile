package com.epita.filrouge.expositions.exception;

import com.epita.filrouge.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
    public class FooMapperExceptionCode {

        public HttpStatus mapCodeToHTTPCode(String code) {
            HttpStatus httpStatus;

            switch (code) {
                case BusinessException.ALL_READY_EXIST:
                    httpStatus = HttpStatus.CONFLICT;
                    break;
                case BusinessException.NOT_FOUND:
                    httpStatus = HttpStatus.NOT_FOUND;
                    break;
                default:
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return httpStatus;
        }
    }


