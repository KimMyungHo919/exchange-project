package com.luv2code.exchange.entity;

import com.luv2code.exchange.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('NORMAL', 'CANCELLED')")
    @NotNull
    private ExchangeStatus status;

    public enum ExchangeStatus {
        NORMAL, // 요청중상태
        CANCELLED // 친구상태
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "to_currency_id")
    private Currency currency;

    public Exchange() {

    }

    public Exchange(Double amountInKrw, Double amountAfterExchange, ExchangeStatus status, User user, Currency currency) {
        this.amountInKrw = amountInKrw;
        this.amountAfterExchange = amountAfterExchange;
        this.status = status;
        this.user = user;
        this.currency = currency;
    }

    public void changeStatusCancelled() {
        this.status = ExchangeStatus.CANCELLED;
    }
}
