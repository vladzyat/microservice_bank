package com.vladzyatkovski.task_for_solva.repository;


import com.vladzyatkovski.task_for_solva.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE" +
            "t.senderAccountNumber = :senderAccountNumber AND MONTH(t.transactionDate) = :month")
    BigDecimal sumByAccountAndMonth(@Param("account") String senderAccountNumber,
                                    @Param("month") int month);
}
