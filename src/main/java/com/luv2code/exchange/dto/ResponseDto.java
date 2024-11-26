package com.luv2code.exchange.dto;

import com.luv2code.exchange.entity.Exchange;
import lombok.Getter;

@Getter
public class ResponseDto {

    private Long userId;

    private Long currencyId;

    private Double amountInKrw;

    private Double amountAfterExchange;

    private String status;

    public ResponseDto(Long userId, Long currencyId, Double amountInKrw, Double amountAfterExchange, String status) {
        this.userId = userId;
        this.currencyId = currencyId;
        this.amountInKrw = amountInKrw;
        this.amountAfterExchange = amountAfterExchange;
        this.status = status;
    }

    public ResponseDto(Exchange exchange) {
        this.userId = exchange.getUser().getId();
        this.currencyId = exchange.getCurrency().getId();
        this.amountInKrw = exchange.getAmountInKrw();
        this.amountAfterExchange = exchange.getAmountAfterExchange();
        this.status = exchange.getStatus().name();
    }
}
