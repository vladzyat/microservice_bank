package com.vladzyatkovski.task_for_solva.repository;


import com.vladzyatkovski.task_for_solva.entity.Transaction;
import com.vladzyatkovski.task_for_solva.enumeration.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.senderAccountNumber = :senderAccountNumber " +
            "AND EXTRACT(MONTH FROM t.transactionDate) = :month AND t.expenseCategory = :expenseCategory")
    BigDecimal sumByAccountAndMonthAndCategory(@Param("senderAccountNumber") String senderAccountNumber,
                                    @Param("month") int month,
                                    @Param("expenseCategory") ExpenseCategory expenseCategory);

    @Query("SELECT t FROM Transaction t WHERE t.limitExceeded = true AND t.senderAccountNumber = :senderAccountNumber")
    Set<Transaction> findAllWithLimitExceededByAccount(@Param("senderAccountNumber") String senderAccountNumber);

    @Query("SELECT t FROM Transaction t WHERE t.limitExceeded = true")
    Set<Transaction> findAllWithLimitExceeded();
}
