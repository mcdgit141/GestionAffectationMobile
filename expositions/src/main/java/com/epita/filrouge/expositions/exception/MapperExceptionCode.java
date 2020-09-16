package com.epita.filrouge.expositions.exception;

import com.epita.filrouge.domain.exception.BusinessException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MapperExceptionCode {

        public HttpStatus mapCodeToHTTPCode(String code) {
            HttpStatus httpStatus;

            switch (code) {
                case BusinessException.ALL_READY_EXIST:
                    httpStatus = HttpStatus.CONFLICT;
                    break;
                case BusinessException.ALL_READY_CLOSED:
                    httpStatus = HttpStatus.CONFLICT;
                    break;
                case BusinessException.NOT_FOUND:
                    httpStatus = HttpStatus.NOT_FOUND;
                    break;
                case BusinessException.BAD_REQUEST:
                    httpStatus = HttpStatus.BAD_REQUEST;
                    break;
                default:
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            return httpStatus;
        }
    }


