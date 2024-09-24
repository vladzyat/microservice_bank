package com.vladzyatkovski.task_for_solva.service.impl;

import com.vladzyatkovski.task_for_solva.entity.CurrencyExchangeRate;
import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;
import com.vladzyatkovski.task_for_solva.entity.Transaction;
import com.vladzyatkovski.task_for_solva.repository.CurrencyExchangeRateRepository;
import com.vladzyatkovski.task_for_solva.repository.MonthlyTransactionLimitsRepository;
import com.vladzyatkovski.task_for_solva.repository.TransactionRepository;
import com.vladzyatkovski.task_for_solva.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    CurrencyExchangeRateRepository currencyExchangeRateRepository;
    MonthlyTransactionLimitsRepository monthlyTransactionLimitsRepository;


    @Override
    @Transactional
    public Transaction processTransaction(Transaction transaction) {
        MonthlyTransactionLimit monthlyTransactionLimit = monthlyTransactionLimitsRepository
                .findMonthlyTransactionLimitByAccount(transaction.getSenderAccountNumber());

        BigDecimal monthlySpent = transactionRepository.sumByAccountAndMonth(transaction.getSenderAccountNumber(),
                ZonedDateTime.now().getMonthValue());

        if (monthlySpent == null) {
            monthlySpent = BigDecimal.ZERO;
        }

        boolean isExcededLimit = monthlySpent.add(transaction.getAmount())
                .compareTo(monthlyTransactionLimit.getLimitSum()) > 0;

        transaction.setLimitExceeded(isExcededLimit);

        return transactionRepository.save(transaction);
    }
}
