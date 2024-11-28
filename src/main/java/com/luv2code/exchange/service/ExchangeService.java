package com.luv2code.exchange.service;

import com.luv2code.exchange.dto.*;
import com.luv2code.exchange.entity.Currency;
import com.luv2code.exchange.entity.Exchange;
import com.luv2code.exchange.entity.User;
import com.luv2code.exchange.error.DataNotFoundException;
import com.luv2code.exchange.error.UserNotFoundException;
import com.luv2code.exchange.repository.CurrencyRepository;
import com.luv2code.exchange.repository.ExchangeRepository;
import com.luv2code.exchange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;

    public ResponseDto saveExchangeRequest(ExchangeRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Currency currency = currencyRepository.findById(dto.getCurrencyId())
                .orElseThrow(UserNotFoundException::new);

        BigDecimal amountInKrw = dto.getAmountInKrw();
        BigDecimal exchangeRate = currency.getExchangeRate();

        BigDecimal exchangeAmount = amountInKrw.divide(exchangeRate, 2, RoundingMode.HALF_UP);

        Exchange exchange = new Exchange(dto.getAmountInKrw(),exchangeAmount, Exchange.ExchangeStatus.NORMAL,user,currency);

        Exchange result = exchangeRepository.save(exchange);

        return new ResponseDto(result);
    }

    public List<ResponseDto> getExchangeList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Exchange> exchanges = exchangeRepository.findByUser(user);

        if (exchanges.isEmpty()) {
            throw new DataNotFoundException();
        }

        return exchanges.stream().map(ResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ExchangeSummaryResponseDto> getExchangeSummaryList(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException();
        }

        if (exchangeRepository.getExchangeSummaryByUser(userId).isEmpty()) {
            throw new DataNotFoundException();
        }

        return exchangeRepository.getExchangeSummaryByUser(userId);
    }

    @Transactional
    public UpdateResponseDto updateStatus(Long exchangeId) {
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(UserNotFoundException::new);

        exchange.changeStatusCancelled();

        return new UpdateResponseDto(exchange);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(user.getId());

    }
}
