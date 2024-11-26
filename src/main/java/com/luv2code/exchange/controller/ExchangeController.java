package com.luv2code.exchange.controller;

import com.luv2code.exchange.dto.*;
import com.luv2code.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseDto createExchangeRequest(@RequestBody ExchangeRequestDto dto) {

        return exchangeService.saveExchangeRequest(dto);
    }

    @GetMapping
    public List<ResponseDto> findExchangeListByUserId(@RequestBody RequestDto dto) {

        return exchangeService.getExchangeList(dto);
    }

    @PutMapping
    public UpdateResponseDto updateStatus(@RequestBody UpdateRequestDto dto) {

        return exchangeService.updateStatus(dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody RequestDto dto) {

        exchangeService.deleteUser(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
