package com.vladzyatkovski.task_for_solva.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MonthlyTransactionLimitDTO {

    private String accountNumber;
    private String expenseCategory;  // You can validate with an enum if needed
    private BigDecimal limitSum;
}

