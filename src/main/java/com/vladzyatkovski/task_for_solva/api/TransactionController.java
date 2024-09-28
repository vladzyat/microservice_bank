package com.vladzyatkovski.task_for_solva.api;

import com.vladzyatkovski.task_for_solva.dto.TransactionDTO;
import com.vladzyatkovski.task_for_solva.dto.TransactionIsExceededLimitDTO;
import com.vladzyatkovski.task_for_solva.entity.Transaction;
import com.vladzyatkovski.task_for_solva.service.TransactionService;
import com.vladzyatkovski.task_for_solva.service.TransactionWithExceededLimitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/process")
    public ResponseEntity<String> processTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        try {
            Transaction transaction = new Transaction();
            transaction.setSenderAccountNumber(transactionDTO.getSenderAccountNumber());
            transaction.setReceiverAccountNumber(transactionDTO.getReceiverAccountNumber());
            transaction.setAmount(transactionDTO.getAmount());
            transaction.setExpenseCategory(transactionDTO.getExpenseCategory());
            transaction.setTransactionDate(transactionDTO.getTransactionDate());
            transaction.setCurrency(transactionDTO.getCurrency());

            transactionService.processTransaction(transaction);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
