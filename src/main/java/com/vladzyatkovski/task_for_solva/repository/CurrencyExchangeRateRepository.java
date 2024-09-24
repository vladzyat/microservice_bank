package com.vladzyatkovski.task_for_solva.repository;

import com.vladzyatkovski.task_for_solva.entity.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {

    @Query("SELECT c FROM CurrencyExchangeRate c WHERE c.currencyFrom = :currencyFrom")
    CurrencyExchangeRate findByCurrencyFrom(@Param("currencyFrom") String currencyFrom);

}
