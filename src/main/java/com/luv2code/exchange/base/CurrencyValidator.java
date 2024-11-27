package com.luv2code.exchange.base;

import com.luv2code.exchange.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class CurrencyValidator {

    private final CurrencyRepository currencyRepository;

    @PostConstruct
    public void validateCurrencyData() {
        currencyRepository.findAll().stream()
                .filter(currency -> currency.getExchangeRate().compareTo(BigDecimal.ZERO) <= 0)
                .forEach(currency -> log.warn("통화단위가 잘못되었습니다! 확인해주세요! : {}", currency.getCurrencyName()));
    }
}
