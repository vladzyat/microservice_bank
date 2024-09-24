package com.vladzyatkovski.task_for_solva.repository;

import com.vladzyatkovski.task_for_solva.entity.MonthlyTransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyTransactionLimitsRepository
        extends JpaRepository<MonthlyTransactionLimit, Long> {

    @Query("SELECT * FROM MonthlyTransactionLimit WHERE account = :account;")
    MonthlyTransactionLimit findMonthlyTransactionLimitByAccount(@Param("account") String account);

}
