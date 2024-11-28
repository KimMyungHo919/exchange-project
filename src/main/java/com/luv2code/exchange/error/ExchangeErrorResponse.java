package com.luv2code.exchange.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeErrorResponse {

    private int status;

    private String message;

    private long timeStamp;

    public ExchangeErrorResponse() {}

    public ExchangeErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
