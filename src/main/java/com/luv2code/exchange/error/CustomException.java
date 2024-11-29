package com.luv2code.exchange.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CustomException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.httpStatus = exceptionType.getHttpStatus();
    }
}

// 사용방법 예시
// throw new CustomException(ExceptionType.USER_NOT_FOUND);