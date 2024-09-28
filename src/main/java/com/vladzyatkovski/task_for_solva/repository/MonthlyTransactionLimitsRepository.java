package com.vladzyatkovski.task_for_solva.repository;

import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;
import com.vladzyatkovski.task_for_solva.enumeration.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface MonthlyTransactionLimitsRepository
        extends JpaRepository<MonthlyTransactionLimit, Long> {

    @Query("SELECT m.limitSum FROM MonthlyTransactionLimit m WHERE m.account = :account AND " +
    "m.expenseCategory = :expenseCategory")
    BigDecimal findMonthlyTransactionLimitByAccountAndCategory(@Param("account") String account,
                                                               @Param("expenseCategory")
                                                               ExpenseCategory expenseCategory);

    @Query("SELECT m FROM MonthlyTransactionLimit m WHERE m.account = :account")
    MonthlyTransactionLimit findMonthlyTransactionLimitByAccount(@Param("account") String account);

    @Query("SELECT m FROM MonthlyTransactionLimit m")
    Set<MonthlyTransactionLimit> findAllMonthlyTransactionLimit();

}
