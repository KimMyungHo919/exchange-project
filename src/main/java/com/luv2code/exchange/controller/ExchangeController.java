package com.luv2code.exchange.controller;

import com.luv2code.exchange.dto.ExchangeRequestDto;
import com.luv2code.exchange.dto.ResponseDto;
import com.luv2code.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseDto createExchangeRequest(@RequestBody ExchangeRequestDto dto) {

        return exchangeService.saveExchangeRequest(dto);
    }
}
