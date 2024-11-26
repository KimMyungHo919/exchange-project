package com.luv2code.exchange.repository;

import com.luv2code.exchange.entity.Exchange;
import com.luv2code.exchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    List<Exchange> findByUser(User user);
}
