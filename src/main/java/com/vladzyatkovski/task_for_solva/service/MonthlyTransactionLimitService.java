package com.vladzyatkovski.task_for_solva.service;

import com.vladzyatkovski.task_for_solva.dto.MonthlyTransactionLimitDTO;
import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;

public interface MonthlyTransactionLimitService {

    public MonthlyTransactionLimit setLimit(MonthlyTransactionLimitDTO limitDTO);
}