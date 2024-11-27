package com.luv2code.exchange.dto;

import com.luv2code.exchange.entity.Exchange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateResponseDto {

    @NotNull
    private Long exchangeId;

    @NotBlank
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
