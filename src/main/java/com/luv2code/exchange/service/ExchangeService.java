package com.luv2code.exchange.service;

import com.luv2code.exchange.dto.*;
import com.luv2code.exchange.entity.Currency;
import com.luv2code.exchange.entity.Exchange;
import com.luv2code.exchange.entity.User;
import com.luv2code.exchange.error.CustomException;
import com.luv2code.exchange.error.ExceptionType;
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
     * @throws CustomException 해당아이디의 유저가 없을경우 발생
     */
    public ResponseDto saveExchangeRequest(ExchangeRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        Currency currency = currencyRepository.findById(dto.getCurrencyId())
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

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
     * @throws CustomException 환전 기록이 존재하지 않는 경우 발생
     */
    public List<ResponseDto> getExchangeList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        List<Exchange> exchanges = exchangeRepository.findByUser(user);

        if (exchanges.isEmpty()) {
            throw new CustomException(ExceptionType.USER_NOT_FOUND);
        }

        return exchanges.stream().map(ResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 사용자의 환전 요약 정보를 조회 (userId, 총 환전횟수, 총 환전금액)
     *
     * @param userId User 고유 ID
     * @return ExchangeSummaryResponseDto 리스트
     * @throws CustomException 사용자가 존재하지 않는 경우 발생
     * @throws CustomException 환전 요약 정보가 존재하지 않는 경우 발생
     */
    public List<ExchangeSummaryResponseDto> getExchangeSummaryList(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new CustomException(ExceptionType.USER_NOT_FOUND);
        }

        if (exchangeRepository.getExchangeSummaryByUser(userId).isEmpty()) {
            throw new CustomException(ExceptionType.FILE_NOT_FOUND);
        }

        return exchangeRepository.getExchangeSummaryByUser(userId);
    }

    /**
     * 환전 상태를 취소로 업데이트
     *
     * @param exchangeId 환전 고유 ID
     * @return UpdateResponseDto
     * @throws CustomException 환전 ID에 해당하는 환전 정보가 존재하지 않는 경우 발생
     */
    @Transactional
    public UpdateResponseDto updateStatus(Long exchangeId) {
        Exchange exchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        exchange.changeStatusCancelled();

        return new UpdateResponseDto(exchange);
    }

    /**
     * 사용자를 삭제
     *
     * @param userId User 고유 ID
     * @throws CustomException 사용자가 존재하지 않는 경우 발생
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        userRepository.deleteById(user.getId());
    }
}
