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

    /**
     * 환전요청 저장
     *
     * @param dto 환전 요청에 대한 데이터
     * @return ResponseDto
     * @throws UserNotFoundException 해당아이디의 유저가 없을경우 발생
     */
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

    /**
     * 환전 목록 조회
     *
     * @param userId User 고유 ID
     * @return ResponseDto 리스트
     * @throws DataNotFoundException 환전 기록이 존재하지 않는 경우 발생
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우 발생
     */
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

    /**
     * 사용자의 환전 요약 정보를 조회 (userId, 총 환전횟수, 총 환전금액)
     *
     * @param userId User 고유 ID
     * @return ExchangeSummaryResponseDto 리스트
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우 발생
     * @throws DataNotFoundException 환전 요약 정보가 존재하지 않는 경우 발생
     */
    public List<ExchangeSummaryResponseDto> getExchangeSummaryList(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException();
        }

        if (exchangeRepository.getExchangeSummaryByUser(userId).isEmpty()) {
            throw new DataNotFoundException();
        }

        return exchangeRepository.getExchangeSummaryByUser(userId);
    }

    /**
     * 환전 상태를 취소로 업데이트
     *
     * @param exchangeId 환전 고유 ID
     * @return UpdateResponseDto
     * @throws UserNotFoundException 환전 ID에 해당하는 환전 정보가 존재하지 않는 경우 발생
     */
    @Transactional
    public UpdateResponseDto updateStatus(Long exchangeId) {
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(UserNotFoundException::new);

        exchange.changeStatusCancelled();

        return new UpdateResponseDto(exchange);
    }

    /**
     * 사용자를 삭제
     *
     * @param userId User 고유 ID
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우 발생
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(user.getId());
    }
}
