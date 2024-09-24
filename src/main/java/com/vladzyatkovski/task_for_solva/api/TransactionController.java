package com.vladzyatkovski.task_for_solva.api;

import com.vladzyatkovski.task_for_solva.entity.Transaction;
import com.vladzyatkovski.task_for_solva.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/process")
    public ResponseEntity<Transaction> processTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction processedTransaction = transactionService.processTransaction(transaction);
            return new ResponseEntity<>(processedTransaction, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
