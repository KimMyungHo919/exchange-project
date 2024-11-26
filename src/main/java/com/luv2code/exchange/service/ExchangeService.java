package com.luv2code.exchange.service;

import com.luv2code.exchange.dto.*;
import com.luv2code.exchange.entity.Currency;
import com.luv2code.exchange.entity.Exchange;
import com.luv2code.exchange.entity.User;
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
                .orElseThrow(()->new UserNotFoundException("User ID를 찾을 수 없습니다. ID를 다시 확인해주세요."));

        Currency currency = currencyRepository.findById(dto.getCurrencyId())
                .orElseThrow(()->new UserNotFoundException("Currency ID를 찾을 수 없습니다. ID를 다시 확인해주세요."));

        BigDecimal amountInKrw = dto.getAmountInKrw();
        BigDecimal exchangeRate = currency.getExchangeRate();

        BigDecimal exchangeAmount = amountInKrw.divide(exchangeRate, 2, RoundingMode.HALF_UP);

        Exchange exchange = new Exchange(dto.getAmountInKrw(),exchangeAmount, Exchange.ExchangeStatus.NORMAL,user,currency);

        Exchange result = exchangeRepository.save(exchange);

        return new ResponseDto(result);
    }

    public List<ResponseDto> getExchangeList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User ID를 찾을 수 없습니다. ID를 다시 확인해주세요."));

        List<Exchange> exchanges = exchangeRepository.findByUser(user);

        return exchanges.stream().map(
                exchange -> new ResponseDto(exchange))
                .collect(Collectors.toList());
    }

    public List<ExchangeSummaryResponseDto> getExchangeSummaryList(Long userId) {
        if (exchangeRepository.getExchangeSummaryByUser(userId).isEmpty()) {
            throw new UserNotFoundException("User ID를 찾을 수 없습니다. ID를 다시 확인해주세요.");
        }

        return exchangeRepository.getExchangeSummaryByUser(userId);
    }

    @Transactional
    public UpdateResponseDto updateStatus(Long exchangeId) {
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(()->new UserNotFoundException("Exchange ID를 찾을 수 없습니다. ID를 다시 확인해주세요."));

        exchange.changeStatusCancelled();

        return new UpdateResponseDto(exchange);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User ID를 찾을 수 없습니다. ID를 다시 확인해주세요."));

        userRepository.deleteById(user.getId());

    }
}
