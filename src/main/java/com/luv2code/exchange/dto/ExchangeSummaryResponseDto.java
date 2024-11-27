package com.luv2code.exchange.dto;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class ExchangeSummaryResponseDto {

    private Long userId;

    private Long count;

    private BigDecimal totalAmountInKrw;

    public ExchangeSummaryResponseDto(Long userId, Long count, BigDecimal totalAmountInKrw) {
        this.userId = userId;
        this.count = count;
        this.totalAmountInKrw = totalAmountInKrw;
    }
}
