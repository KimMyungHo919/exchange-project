package com.luv2code.exchange.dto;

import com.luv2code.exchange.entity.Exchange;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ResponseDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long currencyId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "금액은 0보다 커야 합니다.")
    @Digits(integer = 10, fraction = 2, message = "금액은 최대 10자리 정수와 소수점 2자리까지 입력 가능합니다.")
    private BigDecimal amountInKrw;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "금액은 0보다 커야 합니다.")
    @Digits(integer = 10, fraction = 2, message = "금액은 최대 10자리 정수와 소수점 2자리까지 입력 가능합니다.")
    private BigDecimal amountAfterExchange;

    @NotBlank
    private String status;

    public ResponseDto(Long userId, Long currencyId, BigDecimal amountInKrw, BigDecimal amountAfterExchange, String status) {
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
