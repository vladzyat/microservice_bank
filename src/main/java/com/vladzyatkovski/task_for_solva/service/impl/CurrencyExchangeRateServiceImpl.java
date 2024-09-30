package com.vladzyatkovski.task_for_solva.service.impl;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.exchangerate.ExchangeRate;
import com.crazzyghost.alphavantage.exchangerate.ExchangeRateResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladzyatkovski.task_for_solva.entity.CurrencyExchangeRate;
import com.vladzyatkovski.task_for_solva.enumeration.Currency;
import com.vladzyatkovski.task_for_solva.repository.CurrencyExchangeRateRepository;
import com.vladzyatkovski.task_for_solva.service.CurrencyExchangeRateService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
@Data
public class CurrencyExchangeRateServiceImpl implements CurrencyExchangeRateService {

    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Value("${exchangeratesapi.api.key}")
    private String exchangeratesapi;

    private static final String EXCHANGE_RATES_URL = "https://api.exchangeratesapi.io/v1/latest?access_key=";


    @PostConstruct
    public void init() {
        updateCurrencyRates();
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleCurrencyRateUpdate() {
        updateCurrencyRates();
    }

    @Transactional
    @Override
    public void updateCurrencyRates() {

        RestTemplate restTemplate = new RestTemplate();

        String url = EXCHANGE_RATES_URL + exchangeratesapi +  "&symbols=RUB,KZT";

        JsonNode latestRates = fetchRates(restTemplate, url);

        if (latestRates != null && latestRates.has("rates")) {
            JsonNode ratesNode = latestRates.get("rates");
            saveRatesToDatabase(ratesNode);
            System.out.println("Exchange Rates successfully updated");
        }
    }

    private JsonNode fetchRates(RestTemplate restTemplate, String url) {
        try {
            return restTemplate.getForObject(url, JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveRatesToDatabase(JsonNode ratesNode) {
        Iterator<String> currencyCodes = ratesNode.fieldNames();
        while (currencyCodes.hasNext()) {
            String currencyCode = currencyCodes.next();
            BigDecimal exchangeRate = ratesNode.get(currencyCode).decimalValue();

            CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate();
            currencyExchangeRate.setCurrencyFrom(Currency.valueOf(currencyCode));
            currencyExchangeRate.setTargetCurrency(Currency.USD);
            currencyExchangeRate.setExchangeRate(exchangeRate);
            currencyExchangeRate.setRateTimestamp(ZonedDateTime.now());

            currencyExchangeRateRepository.save(currencyExchangeRate);
        }
    }

    public BigDecimal convertToUSD(BigDecimal amount, Currency currencyFrom){

        BigDecimal exchangeRate = currencyExchangeRateRepository
                .findRateByCurrencyFrom(currencyFrom);

        if(exchangeRate == null){
            exchangeRate = BigDecimal.ZERO;
        }

        return amount.multiply(exchangeRate);
    }
}
