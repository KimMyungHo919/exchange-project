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

    // 환전요청 저장
    @PostMapping
    public ResponseEntity<ResponseDto> createExchangeRequest(@RequestBody ExchangeRequestDto dto) {
        return new ResponseEntity<>(exchangeService.saveExchangeRequest(dto), HttpStatus.CREATED);
    }

    // 환전 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<ResponseDto>> findExchangeListByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(exchangeService.getExchangeList(userId), HttpStatus.OK);
    }

    // 사용자의 환전 요약 정보를 조회 (userId, 총 환전횟수, 총 환전금액)
    @GetMapping("/summaries/{userId}")
    public ResponseEntity<List<ExchangeSummaryResponseDto>> findExchangeSummaryList(@PathVariable Long userId) {
        return new ResponseEntity<>(exchangeService.getExchangeSummaryList(userId), HttpStatus.OK);
    }

    // 환전 상태를 취소로 업데이트
    @PutMapping("/{exchangeId}")
    public ResponseEntity<UpdateResponseDto> updateStatus(@PathVariable Long exchangeId) {
        return new ResponseEntity<>(exchangeService.updateStatus(exchangeId), HttpStatus.OK);
    }

    // 사용자를 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        exchangeService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
