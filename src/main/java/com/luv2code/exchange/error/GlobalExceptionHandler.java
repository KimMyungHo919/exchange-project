package com.luv2code.exchange.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleException(CustomException exception) {

        ErrorResponseDto error = new ErrorResponseDto(
                exception.getHttpStatus(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(error);
//        return new ResponseEntity<>(error, exception.getErrorCode());
    }
}
