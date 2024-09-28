package com.vladzyatkovski.task_for_solva.service;

import com.vladzyatkovski.task_for_solva.dto.MonthlyTransactionLimitDTO;
import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;
import com.vladzyatkovski.task_for_solva.enumeration.ExpenseCategory;
import com.vladzyatkovski.task_for_solva.repository.MonthlyTransactionLimitsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonthlyTransactionLimitServiceImpl implements MonthlyTransactionLimitService {

    private final MonthlyTransactionLimitsRepository monthlyTransactionLimitsRepository;

    @Transactional
    public MonthlyTransactionLimit setLimit(MonthlyTransactionLimitDTO limitDTO) {
        MonthlyTransactionLimit limit = new MonthlyTransactionLimit();
        limit.setAccount(limitDTO.getAccountNumber());
        limit.setExpenseCategory(ExpenseCategory.valueOf(limitDTO.getExpenseCategory()));
        limit.setLimitSum(limitDTO.getLimitSum());
        limit.setLimitDateTime(java.time.ZonedDateTime.now());

        return monthlyTransactionLimitsRepository.save(limit);
    }
}

