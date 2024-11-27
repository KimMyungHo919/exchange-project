package com.luv2code.exchange.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestDto {

    @NotNull
    private Long userId;
}
