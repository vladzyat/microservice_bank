package com.vladzyatkovski.task_for_solva.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.vladzyatkovski.task_for_solva.enumeration.ExpenseCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "monthly_transaction_limits")
@Data
@RequiredArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Column(name = "limit_sum", nullable = false, precision = 19, scale = 4)
    private BigDecimal limitSum;
}
