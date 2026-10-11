
package com.teja.currency_converter.model;

import java.math.BigDecimal;

public record ConversionRequest(
        BigDecimal amount,
        String fromCurrency,
        String toCurrency) {
}
