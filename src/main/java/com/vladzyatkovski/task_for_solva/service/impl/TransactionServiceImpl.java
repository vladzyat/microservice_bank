package com.vladzyatkovski.task_for_solva.service.impl;

import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;
import com.vladzyatkovski.task_for_solva.entity.Transaction;
import com.vladzyatkovski.task_for_solva.enumeration.Currency;
import com.vladzyatkovski.task_for_solva.repository.CurrencyExchangeRateRepository;
import com.vladzyatkovski.task_for_solva.repository.MonthlyTransactionLimitsRepository;
import com.vladzyatkovski.task_for_solva.repository.TransactionRepository;
import com.vladzyatkovski.task_for_solva.service.TransactionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Data
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;
    private final MonthlyTransactionLimitsRepository monthlyTransactionLimitsRepository;


    @Override
    @Transactional
    public Transaction processTransaction(Transaction transaction) {

        BigDecimal monthlyTransactionLimit = monthlyTransactionLimitsRepository
                .findMonthlyTransactionLimitByAccountAndCategory(transaction.getSenderAccountNumber(),
                        transaction.getExpenseCategory());

        BigDecimal monthlySpent = transactionRepository
                .sumByAccountAndMonthAndCategory(transaction.getSenderAccountNumber(),
                transaction.getTransactionDate().getMonthValue(), transaction.getExpenseCategory());

        if (monthlySpent == null) {
            monthlySpent = BigDecimal.ZERO;
        }

        transaction.setLimitExceeded(getIsExceededLimit(transaction, monthlySpent,
                monthlyTransactionLimit));

        return transactionRepository.save(transaction);
    }

    private boolean getIsExceededLimit(Transaction transaction, BigDecimal monthlySpent,
                                      BigDecimal monthlyTransactionLimit){

        if(monthlyTransactionLimit == null){
            setDefaultMonthlyLimit(transaction);
            monthlyTransactionLimit = new BigDecimal("1000.0");
        }
        BigDecimal transactionAmountInUsd = null;

        if(!transaction.getCurrency().equals(Currency.USD)){
            BigDecimal exchangeRate = currencyExchangeRateRepository
                    .findRateByCurrencyFrom(transaction.getCurrency());
            monthlySpent = monthlySpent.multiply(exchangeRate);
        }

        return monthlySpent.add(transaction.getAmount()).compareTo(monthlyTransactionLimit) > 0;
    }

    @Transactional
    public void setDefaultMonthlyLimit(Transaction transaction){
        BigDecimal defaultMonthlyLimitSum = new BigDecimal("1000.0");

        MonthlyTransactionLimit defaultMonthlyLimit = new MonthlyTransactionLimit();

        defaultMonthlyLimit.setAccount(transaction.getSenderAccountNumber());
        defaultMonthlyLimit.setLimitDateTime(ZonedDateTime.now());
        defaultMonthlyLimit.setLimitSum(defaultMonthlyLimitSum);
        defaultMonthlyLimit.setExpenseCategory(transaction.getExpenseCategory());

        monthlyTransactionLimitsRepository.save(defaultMonthlyLimit);
    }
}
