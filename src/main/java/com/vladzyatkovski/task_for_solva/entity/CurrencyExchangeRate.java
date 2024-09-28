package com.vladzyatkovski.task_for_solva.entity;

import com.vladzyatkovski.task_for_solva.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "currency_exchange_rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_id")
    private Long uniqueId;

    @Column(name = "currency_from", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currencyFrom;

    @Column(name = "target_currency", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency targetCurrency;

    @Column(name = "exchange_rate", precision = 19, scale = 6, nullable = false)
    private BigDecimal exchangeRate;


    @Column(name = "rate_timestamp", nullable = false)
    private ZonedDateTime rateTimestamp;
}
