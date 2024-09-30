package com.vladzyatkovski.task_for_solva.service;

import com.vladzyatkovski.task_for_solva.entity.CurrencyExchangeRate;
import com.vladzyatkovski.task_for_solva.enumeration.Currency;

import java.math.BigDecimal;
import java.util.Set;

public interface CurrencyExchangeRateService {

    public void updateCurrencyRates();

    public BigDecimal convertToUSD(BigDecimal amount, Currency currencyFrom);
}
