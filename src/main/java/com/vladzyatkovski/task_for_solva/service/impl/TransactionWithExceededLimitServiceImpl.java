package com.vladzyatkovski.task_for_solva.service.impl;

import com.vladzyatkovski.task_for_solva.dto.TransactionIsExceededLimitDTO;
import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;
import com.vladzyatkovski.task_for_solva.entity.Transaction;
import com.vladzyatkovski.task_for_solva.repository.MonthlyTransactionLimitsRepository;
import com.vladzyatkovski.task_for_solva.repository.TransactionRepository;
import com.vladzyatkovski.task_for_solva.service.TransactionWithExceededLimitService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
public class TransactionWithExceededLimitServiceImpl implements TransactionWithExceededLimitService {

    private final TransactionRepository transactionRepository;
    private final MonthlyTransactionLimitsRepository monthlyTransactionLimitsRepository;

    @Override
    public Set<TransactionIsExceededLimitDTO> getTransactionsWithExceededLimitByAccount(String senderAccountNumber) {

        Set<TransactionIsExceededLimitDTO> transactionIsExceededLimitDTOS = new HashSet<>();

        Set<Transaction> transactionsWithLimitExceeded = transactionRepository
                .findAllWithLimitExceededByAccount(senderAccountNumber);

        if(transactionsWithLimitExceeded == null){
            return transactionIsExceededLimitDTOS;
        }

        MonthlyTransactionLimit monthlyTransactionLimit = monthlyTransactionLimitsRepository
                .findMonthlyTransactionLimitByAccount(senderAccountNumber);

        if (monthlyTransactionLimit == null) {
            throw new IllegalArgumentException("Monthly transaction limit not found for account: " + senderAccountNumber);
        }

        transactionsWithLimitExceeded.forEach(transaction -> {

            TransactionIsExceededLimitDTO dto = new TransactionIsExceededLimitDTO();

            dto.setSenderAccountNumber(senderAccountNumber);
            dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
            dto.setTransactionAmount(transaction.getAmount());
            dto.setTransactionExpenseCategory(transaction.getExpenseCategory());
            dto.setTransactionCurrency(transaction.getCurrency());
            dto.setLimitAmount(monthlyTransactionLimit.getLimitSum());
            dto.setLimitTimestamp(monthlyTransactionLimit.getLimitDateTime());

            transactionIsExceededLimitDTOS.add(dto);
        });

        return transactionIsExceededLimitDTOS;
    }

    @Override
    public Set<TransactionIsExceededLimitDTO> getTransactionsWithExceededLimit() {

        Set<TransactionIsExceededLimitDTO> transactionIsExceededLimitDTOS = new HashSet<>();

        Set<Transaction> transactionsWithLimitExceeded = transactionRepository
                .findAllWithLimitExceeded();

        if(transactionsWithLimitExceeded == null){
            return transactionIsExceededLimitDTOS;
        }

        Set<MonthlyTransactionLimit> monthlyTransactionLimits = monthlyTransactionLimitsRepository
                .findAllMonthlyTransactionLimit();

        if (monthlyTransactionLimits.isEmpty()) {
            throw new IllegalArgumentException("Monthly transaction limit not found for account: ");
        }

        transactionsWithLimitExceeded.forEach(transaction -> {

            TransactionIsExceededLimitDTO dto = new TransactionIsExceededLimitDTO();

            monthlyTransactionLimits.forEach(limit ->{

            dto.setSenderAccountNumber(transaction.getSenderAccountNumber());
            dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
            dto.setTransactionAmount(transaction.getAmount());
            dto.setTransactionExpenseCategory(transaction.getExpenseCategory());
            dto.setTransactionCurrency(transaction.getCurrency());
            dto.setLimitAmount(limit.getLimitSum());
            dto.setLimitTimestamp(limit.getLimitDateTime());

            transactionIsExceededLimitDTOS.add(dto);
            });
        });

        return transactionIsExceededLimitDTOS;
    }
}
