package com.luv2code.exchange.error;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonPropertyOrder({"status", "errorCode", "message"}) // 필드 순서를 지정
public class ErrorResponseDto {

    private int status;
    private String message;

    public ErrorResponseDto(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
    }
}
