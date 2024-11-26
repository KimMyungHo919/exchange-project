package com.luv2code.exchange.service;

import com.luv2code.exchange.dto.ExchangeRequestDto;
import com.luv2code.exchange.dto.ResponseDto;
import com.luv2code.exchange.entity.Currency;
import com.luv2code.exchange.entity.Exchange;
import com.luv2code.exchange.entity.User;
import com.luv2code.exchange.repository.CurrencyRepository;
import com.luv2code.exchange.repository.ExchangeRepository;
import com.luv2code.exchange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    public ResponseDto saveExchangeRequest(ExchangeRequestDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()->new RuntimeException("해당아이디없음."));

        Currency currency = currencyRepository.findById(dto.getCurrencyId())
                .orElseThrow(()->new RuntimeException("해당아이피없음."));

        Double exchangeAmount = dto.getAmountInKrw() / currency.getExchangeRate();

        Exchange exchange = new Exchange(dto.getAmountInKrw(),exchangeAmount, Exchange.ExchangeStatus.NORMAL,user,currency);

        Exchange result = exchangeRepository.save(exchange);

        return new ResponseDto(result);
    }
}
