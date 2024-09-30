package com.vladzyatkovski.task_for_solva.service;


import com.vladzyatkovski.task_for_solva.dto.TransactionDTO;
import com.vladzyatkovski.task_for_solva.entity.Transaction;

public interface TransactionService {

    public Transaction processTransaction(TransactionDTO transactionDTO);
}
