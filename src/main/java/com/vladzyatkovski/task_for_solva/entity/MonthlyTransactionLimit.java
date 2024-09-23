package com.vladzyatkovski.task_for_solva.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "monthly_transaction_limits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyTransactionLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_id")
    private Long uniqueId;

    @Column(name = "account", length = 10, nullable = false)
    private String account;

    @Column(name = "limit_datetime", nullable = false)
    private ZonedDateTime limitDateTime;

    @Column(name = "expense_category", length = 50)
    private String expenseCategory;

    @Column(name = "limit_sum", nullable = false, precision = 19, scale = 4)
    private BigDecimal limitSum;

    @Column(name = "limit_currency_shortname", length = 3)
    private String limitCurrencyShortname;

}
