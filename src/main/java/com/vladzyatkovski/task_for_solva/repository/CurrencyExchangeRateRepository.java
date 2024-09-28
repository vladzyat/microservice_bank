package com.vladzyatkovski.task_for_solva.repository;

import com.vladzyatkovski.task_for_solva.entity.CurrencyExchangeRate;
import com.vladzyatkovski.task_for_solva.enumeration.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {

    @Query("SELECT c FROM CurrencyExchangeRate c WHERE c.currencyFrom = :currencyFrom")
    CurrencyExchangeRate findByCurrencyFrom(@Param("currencyFrom") Currency currencyFrom);

    @Query("SELECT c.exchangeRate FROM CurrencyExchangeRate c WHERE c.currencyFrom = :currencyFrom")
    BigDecimal findRateByCurrencyFrom(@Param("currencyFrom") Currency currencyFrom);
}
