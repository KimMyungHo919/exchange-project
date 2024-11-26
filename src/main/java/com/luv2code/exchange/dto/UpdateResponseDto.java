package com.luv2code.exchange.dto;

import com.luv2code.exchange.entity.Exchange;
import lombok.Getter;

@Getter
public class UpdateResponseDto {

    private Long exchangeId;

    private String status;

    public UpdateResponseDto(Long exchangeId, String status) {
        this.exchangeId = exchangeId;
        this.status = status;
    }

    public UpdateResponseDto(Exchange exchange) {
        this.exchangeId = exchange.getId();
        this.status = exchange.getStatus().name();
    }
}
