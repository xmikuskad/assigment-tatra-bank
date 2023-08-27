package com.dominikmikuska.assigment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "exchange_rate")
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="from_currency")
    private CurrencyType fromCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name="to_currency")
    private CurrencyType toCurrency;

    @Column(name="value")
    private BigDecimal value;
}
