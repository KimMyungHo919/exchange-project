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

    @GetMapping("/{userId}")
    public List<ResponseDto> findExchangeListByUserId(@PathVariable Long userId) {
        return exchangeService.getExchangeList(userId);
    }

    @GetMapping("/summaries/{userId}")
    public List<ExchangeSummaryResponseDto> findExchangeSummaryList(@PathVariable Long userId) {
        return exchangeService.getExchangeSummaryList(userId);
    }

    @PutMapping("/{exchangeId}")
    public UpdateResponseDto updateStatus(@PathVariable Long exchangeId) {
        return exchangeService.updateStatus(exchangeId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        exchangeService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
