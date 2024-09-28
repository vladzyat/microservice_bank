package com.vladzyatkovski.task_for_solva.dto;

import com.vladzyatkovski.task_for_solva.enumeration.Currency;
import com.vladzyatkovski.task_for_solva.enumeration.ExpenseCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class TransactionIsExceededLimitDTO {

    @NotNull(message = "Sender account number cannot be null")
    @Size(min = 10, max = 10, message = "Sender account number must be 10 digits")
    private String senderAccountNumber;

    @NotNull(message = "Receiver account number cannot be null")
    @Size(min = 10, max = 10, message = "Receiver account number must be 10 digits")
    private String receiverAccountNumber;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be Decimal")
    private BigDecimal transactionAmount;

    @NotNull(message = "Expense category cannot be null")
    private ExpenseCategory transactionExpenseCategory;

    @NotNull(message = "Transaction date cannot be null")
    @Past(message = "transactionDate must be in past or present")
    private ZonedDateTime transactionDate;

    @NotNull(message = "Currency cannot be null")
    private Currency transactionCurrency;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be Decimal")
    private BigDecimal limitAmount;

    @NotNull(message = "Transaction date cannot be null")
    @Past(message = "transactionDate must be in past or present")
    private ZonedDateTime limitTimestamp;

    @NotNull(message = "Currency cannot be null")
    private final Currency limitCurrency = Currency.USD ;

}
