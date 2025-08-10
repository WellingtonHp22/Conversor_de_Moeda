package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionHistory {
    private final String fromCurrency;
    private final String toCurrency;
    private final double originalAmount;
    private final double convertedAmount;
    private final double exchangeRate;
    private final LocalDateTime timestamp;
    private final DateTimeFormatter formatter;

    public ConversionHistory(String fromCurrency, String toCurrency, double originalAmount, double convertedAmount, double exchangeRate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
        this.timestamp = LocalDateTime.now();
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %.2f %s => %.2f %s (Taxa: %.4f)",
                timestamp.format(formatter),
                originalAmount, fromCurrency,
                convertedAmount, toCurrency,
                exchangeRate);
    }
}
