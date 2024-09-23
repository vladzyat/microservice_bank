package com.vladzyatkovski.task_for_solva.service.impl;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.exchangerate.ExchangeRate;
import com.crazzyghost.alphavantage.exchangerate.ExchangeRateResponse;
import com.vladzyatkovski.task_for_solva.entity.CurrencyExchangeRate;
import com.vladzyatkovski.task_for_solva.repository.CurrencyExchangeRateRepository;
import com.vladzyatkovski.task_for_solva.service.CurrencyExchangeRateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class CurrencyExchangeRateServiceImpl implements CurrencyExchangeRateService {

    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Override
    public void updateCurrencyRates() {
        Config cfg = Config.builder()
                .key("#&AJ2OTQ2ZEP0GAZNC")
                .timeOut(100)
                .build();

        AlphaVantage.api().init(cfg);

        String baseCurrency = "USD";
        String[] currencies = { "KZT", "RUB"};

        for (String currency : currencies) {

            AlphaVantage.api()
                    .exchangeRate()
                    .fromCurrency(currency)
                    .toCurrency(baseCurrency)
                    .onSuccess(response -> handleSuccess(response, baseCurrency, currency))
                    .onFailure(error -> handleFailure(error, currency))
                    .fetch();
        }
    }

    private void handleSuccess(ExchangeRateResponse response, String targetCurrency, String currency) {

        BigDecimal exchangeRate = BigDecimal.valueOf(response.getExchangeRate());
        ZonedDateTime timestamp = ZonedDateTime.now();

        CurrencyExchangeRate existingExchangeRate = currencyExchangeRateRepository.findByCurrency(targetCurrency);

        if(existingExchangeRate == null){
            existingExchangeRate = new CurrencyExchangeRate();
            existingExchangeRate.setCurrencyFrom(currency);
            existingExchangeRate.setTargetCurrency(targetCurrency);
        }

        existingExchangeRate.setExchangeRate(exchangeRate);
        existingExchangeRate.setRateTimestamp(timestamp);

        currencyExchangeRateRepository.save(existingExchangeRate);

        System.out.println("Successfully updated rate for " + targetCurrency + ": " + exchangeRate);
    }


    private void handleFailure(AlphaVantageException error, String currency) {
        System.err.println("Failed to update rate for " + currency + ": " + error.getMessage());
    }
}
