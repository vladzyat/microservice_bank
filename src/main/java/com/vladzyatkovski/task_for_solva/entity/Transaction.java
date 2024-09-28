package com.vladzyatkovski.task_for_solva.entity;

import com.vladzyatkovski.task_for_solva.enumeration.Currency;
import com.vladzyatkovski.task_for_solva.enumeration.ExpenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sender_account_number", length = 10, nullable = false)
    private String senderAccountNumber;

    @Column(name = "receiver_account_number", length = 10, nullable = false)
    private String receiverAccountNumber;

    @Column(name ="amount", precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "expense_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Column(name = "transaction_date", nullable = false)
    private ZonedDateTime transactionDate;

    @Column(name = "limit_exceeded", nullable = false)
    private boolean limitExceeded;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
}

