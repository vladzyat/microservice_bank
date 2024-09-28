package com.vladzyatkovski.task_for_solva.service;

import com.vladzyatkovski.task_for_solva.dto.TransactionIsExceededLimitDTO;

import java.util.List;
import java.util.Set;

public interface TransactionWithExceededLimitService {

    public Set<TransactionIsExceededLimitDTO> getTransactionsWithExceededLimitByAccount(String senderAccountNumber);

    public Set<TransactionIsExceededLimitDTO> getTransactionsWithExceededLimit();

}
