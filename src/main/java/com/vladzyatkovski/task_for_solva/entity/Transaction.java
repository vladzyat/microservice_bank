package com.vladzyatkovski.task_for_solva.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


@Entity

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String senderAccountNumber;  // Account making the transaction

    @Column(length = 10, nullable = false)
    private String receiverAccountNumber;  // Account receiving the transaction

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    private String expenseCategory;

    @Column(nullable = false)
    private ZonedDateTime transactionDate;

}

