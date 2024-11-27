package com.luv2code.exchange.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter

public class ExchangeRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long currencyId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "금액은 0보다 커야 합니다.")
    @Digits(integer = 10, fraction = 2, message = "금액은 최대 10자리 정수와 소수점 2자리까지 입력 가능합니다.")
    private BigDecimal amountInKrw;

}
