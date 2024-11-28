package com.luv2code.exchange.repository;

import com.luv2code.exchange.dto.ExchangeSummaryResponseDto;
import com.luv2code.exchange.entity.Exchange;
import com.luv2code.exchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findByUser(User user);

    @Query("SELECT new com.luv2code.exchange.dto.ExchangeSummaryResponseDto(" +
            "e.user.id, COUNT(e), SUM(e.amountInKrw)) " +
            "FROM Exchange e " +
            "WHERE e.user.id = :userId AND e.status = 'NORMAL' " +
            "GROUP BY e.user")
    List<ExchangeSummaryResponseDto> getExchangeSummaryByUser(@Param("userId") Long userId);
}
