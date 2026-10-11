
package com.teja.currency_converter.service;

import com.teja.currency_converter.model.Conversion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class CurrencyService {

    private final ConversionService conversionService;

    public CurrencyService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    // Demo rates relative to INR; these are not live rates.
    private static final Map<String, BigDecimal> RATES = Map.of(
            "INR", new BigDecimal("1.00"),
            "USD", new BigDecimal("83.00"),
            "EUR", new BigDecimal("90.00"),
            "GBP", new BigDecimal("105.00"),
            "JPY", new BigDecimal("0.56"),
            "AED", new BigDecimal("22.60")
    );

    public Conversion convert(
            BigDecimal amount,
            String fromCurrency,
            String toCurrency) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        if (fromCurrency == null || toCurrency == null) {
            throw new IllegalArgumentException(
                    "Select both currencies");
        }

        fromCurrency = fromCurrency.trim().toUpperCase();
        toCurrency = toCurrency.trim().toUpperCase();

        BigDecimal fromRate = RATES.get(fromCurrency);
        BigDecimal toRate = RATES.get(toCurrency);

        if (fromRate == null || toRate == null) {
            throw new IllegalArgumentException(
                    "Unsupported currency");
        }

        // Convert through INR.
        BigDecimal exchangeRate = fromRate.divide(
                toRate, 8, RoundingMode.HALF_UP);

        BigDecimal convertedAmount = amount
                .multiply(exchangeRate)
                .setScale(2, RoundingMode.HALF_UP);

        Conversion conversion = new Conversion();
        conversion.setAmount(amount);
        conversion.setFromCurrency(fromCurrency);
        conversion.setToCurrency(toCurrency);
        conversion.setExchangeRate(exchangeRate);
        conversion.setConvertedAmount(convertedAmount);

        return conversionService.create(conversion);
    }
}
