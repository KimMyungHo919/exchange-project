package com.luv2code.exchange.entity;

import com.luv2code.exchange.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Table(name = "currency")
public class Currency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "currency")
    private List<Exchange> exchanges;
}
