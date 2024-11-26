package com.luv2code.exchange.entity;

import com.luv2code.exchange.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "exchange")
@Entity
public class Exchange extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount_in_krw")
    private Double amountInKrw;

    @Column(name = "amount_after_exchange")
    private Double amountAfterExchange;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "to_currency_id")
    private Currency currency;

    public Exchange() {

    }
}
