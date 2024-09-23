package com.vladzyatkovski.task_for_solva.repository;

import com.vladzyatkovski.task_for_solva.entity.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Integer> {

    @Query("SELECT c FROM CurrencyExchangeRate c WHERE c.currency = :currency ORDER BY c.rateTimestamp DESC")
    CurrencyExchangeRate findByCurrency(String currency);
}
