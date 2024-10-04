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
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
@Data
public class CurrencyExchangeRateServiceImpl implements CurrencyExchangeRateService {

    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Value("${exchangeratesapi.api.key}")
    private String exchangeratesapi;

    private static final String EXCHANGE_RATES_URL = "https://api.twelvedata.com/time_series?apikey=";
    private final String [] currencyPairs = {"RUB/USD"};


    @PostConstruct
    public void init() {
        System.out.println("API Key: " + exchangeratesapi);
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
        String url = null;
        System.out.println("Started updating");

        for(String pair : currencyPairs){
            url = EXCHANGE_RATES_URL + exchangeratesapi + "&symbol=" + pair
                    + "&interval=1day&outputsize=5";

            System.out.println("Next step fetching");

            JsonNode rootNode = fetchRates(restTemplate, url);

            System.out.println("fetching success");

            if(rootNode != null && rootNode.get("meta").get("symbol").asText().equals(pair)){
                System.out.println("Started saving");

                JsonNode valuesArray =  rootNode.get("values");
                LocalDate targetDate = LocalDate.now();
                BigDecimal closeValue = getCloseForDateOrPrevious(valuesArray, targetDate);
                String currencyFrom = pair.substring(0, 3);
                System.out.println(currencyFrom);

                CurrencyExchangeRate existingRate = currencyExchangeRateRepository
                        .findByCurrencyFrom(Currency.valueOf(currencyFrom));

                if (existingRate != null) {
                    existingRate.setExchangeRate(closeValue);
                    existingRate.setRateTimestamp(ZonedDateTime.now());
                    currencyExchangeRateRepository.save(existingRate);
                    System.out.println("Currency exchange rate updated: " + existingRate);
                } else {
                    CurrencyExchangeRate newRate = new CurrencyExchangeRate();
                    newRate.setCurrencyFrom(Currency.valueOf(currencyFrom));
                    newRate.setTargetCurrency(Currency.USD);
                    newRate.setExchangeRate(closeValue);
                    newRate.setRateTimestamp(ZonedDateTime.now());
                    currencyExchangeRateRepository.save(newRate);
                    System.out.println("New currency exchange rate saved: " + newRate);
                }

            }
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

    public BigDecimal convertToUSD(BigDecimal amount, Currency currencyFrom){

        BigDecimal exchangeRate = currencyExchangeRateRepository
                .findRateByCurrencyFrom(currencyFrom);

        if(exchangeRate == null){
            exchangeRate = BigDecimal.ZERO;
        }

        return amount.multiply(exchangeRate);
    }

    private static BigDecimal getCloseForDateOrPrevious(JsonNode valuesArray, LocalDate targetDate) {
        Iterator<JsonNode> elements = valuesArray.elements();
        BigDecimal closestCloseValue = null;
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            LocalDate dateInJson = LocalDate.parse(element.get("datetime").asText());

            if (dateInJson.equals(targetDate)) {
                return BigDecimal.valueOf(element.get("close").asDouble());
            }

            if (dateInJson.isBefore(targetDate) && (closestCloseValue == null
                    || dateInJson.isAfter(LocalDate.parse(element.get("datetime").asText())))) {
                closestCloseValue = BigDecimal.valueOf(element.get("close").asDouble());
            }
        }
        return closestCloseValue;
    }
}
