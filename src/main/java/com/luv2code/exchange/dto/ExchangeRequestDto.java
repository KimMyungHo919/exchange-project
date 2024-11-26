package com.luv2code.exchange.dto;

import lombok.Getter;

@Getter
public class ExchangeRequestDto {

    private Long userId;

    private Long currencyId;

    private Double amountInKrw;

}
