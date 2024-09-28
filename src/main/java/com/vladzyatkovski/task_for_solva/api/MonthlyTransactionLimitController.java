package com.vladzyatkovski.task_for_solva.api;

import com.vladzyatkovski.task_for_solva.dto.TransactionIsExceededLimitDTO;
import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;
import com.vladzyatkovski.task_for_solva.dto.MonthlyTransactionLimitDTO;
import com.vladzyatkovski.task_for_solva.service.MonthlyTransactionLimitService;
import com.vladzyatkovski.task_for_solva.service.TransactionWithExceededLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/limits")
@RequiredArgsConstructor
public class MonthlyTransactionLimitController {

    private final MonthlyTransactionLimitService monthlyTransactionLimitService;
    private final TransactionWithExceededLimitService transactionWithExceededLimitService;


    @PostMapping("/set")
    public ResponseEntity<MonthlyTransactionLimit> setTransactionLimit(
            @RequestBody MonthlyTransactionLimitDTO limitDTO) {

        MonthlyTransactionLimit limit = monthlyTransactionLimitService.setLimit(limitDTO);

        if (limit != null) {
            return new ResponseEntity<>(limit, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/exceeded-limit/{senderAccountNumber}")
    public ResponseEntity<Set<TransactionIsExceededLimitDTO>> getTransactionsWithExceededLimitByAccount(
            @PathVariable String senderAccountNumber) {

        try{
            Set<TransactionIsExceededLimitDTO> transactionIsExceededLimitDTOS = transactionWithExceededLimitService
                    .getTransactionsWithExceededLimitByAccount(senderAccountNumber);

            if (transactionIsExceededLimitDTOS.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(transactionIsExceededLimitDTOS);

        }catch (IllegalArgumentException e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/exceeded-limit")
    public ResponseEntity<Set<TransactionIsExceededLimitDTO>> getTransactionsWithExceededLimit() {

        try{
            Set<TransactionIsExceededLimitDTO> transactionIsExceededLimitDTOS = transactionWithExceededLimitService
                    .getTransactionsWithExceededLimit();

            if (transactionIsExceededLimitDTOS.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(transactionIsExceededLimitDTOS);

        }catch (IllegalArgumentException e){
            return ResponseEntity.noContent().build();
        }
    }


}
