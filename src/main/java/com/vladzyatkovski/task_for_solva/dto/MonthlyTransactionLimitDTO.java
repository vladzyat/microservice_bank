package com.vladzyatkovski.task_for_solva.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MonthlyTransactionLimitDTO {

    @NotNull
    @Size(min = 10, max = 10, message = "Account number must be 10 digits")
    private String accountNumber;

    @NotNull(message = "Expense category cannot be null")
    private String expenseCategory;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be Decimal")
    private BigDecimal limitSum;
}

