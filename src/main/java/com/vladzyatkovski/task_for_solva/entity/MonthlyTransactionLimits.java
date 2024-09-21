package com.vladzyatkovski.task_for_solva.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "monthly_transaction_limits")
public class MonthlyTransactionLimits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_id")
    private Integer uniqueId;  // Primary Key

    @Column(name = "account", length = 10, nullable = false)
    private String account;  // Foreign Key reference to account

    @Column(name = "limit_datetime", nullable = false)
    private ZonedDateTime limitDateTime;  // Date and time of the limit

    @Column(name = "expense_category", length = 50)  // Adjust length as needed
    private String expenseCategory;  // Category of the expense

    @Column(name = "limit_sum", nullable = false, precision = 19, scale = 4)
    private BigDecimal limitSum;  // Maximum allowable transaction amount

    @Column(name = "limit_currency_shortname", length = 3)  // Example: "USD", "EUR"
    private String limitCurrencyShortname;  // Currency of the limit

}
