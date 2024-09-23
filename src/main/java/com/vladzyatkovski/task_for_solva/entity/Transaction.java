package com.vladzyatkovski.task_for_solva.entity;

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
    private Long id;

    @Column(length = 10, nullable = false)
    private String senderAccountNumber;

    @Column(length = 10, nullable = false)
    private String receiverAccountNumber;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    private String expenseCategory;

    @Column(nullable = false)
    private ZonedDateTime transactionDate;

    @Column(name = "limit_exceeded", nullable = false)
    private boolean limitExceeded;

}

