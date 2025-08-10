package services;

import models.Conversion;
import models.ConversionHistory;
import models.ExchangeRateResponse;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CurrencyConverter {
    private final ExchangeRateService exchangeRateService;
    private final List<Conversion> availableConversions;
    private final List<ConversionHistory> conversionHistory;
    private final DecimalFormat decimalFormat;

    public CurrencyConverter() {
        this.exchangeRateService = new ExchangeRateService();
        this.availableConversions = initializeConversions();
        this.conversionHistory = new ArrayList<>();
        this.decimalFormat = new DecimalFormat("#,##0.00");
    }

    private List<Conversion> initializeConversions() {
        List<Conversion> conversions = new ArrayList<>();
        conversions.add(new Conversion("USD", "BRL", "Dólar Americano (USD) => Real Brasileiro (BRL)"));
        conversions.add(new Conversion("BRL", "USD", "Real Brasileiro (BRL) => Dólar Americano (USD)"));
        conversions.add(new Conversion("EUR", "BRL", "Euro (EUR) => Real Brasileiro (BRL)"));
        conversions.add(new Conversion("BRL", "EUR", "Real Brasileiro (BRL) => Euro (EUR)"));
        conversions.add(new Conversion("USD", "EUR", "Dólar Americano (USD) => Euro (EUR)"));
        conversions.add(new Conversion("EUR", "USD", "Euro (EUR) => Dólar Americano (USD)"));
        conversions.add(new Conversion("USD", "ARS", "Dólar Americano (USD) => Peso Argentino (ARS)"));
        conversions.add(new Conversion("ARS", "USD", "Peso Argentino (ARS) => Dólar Americano (USD)"));
        conversions.add(new Conversion("USD", "BOB", "Dólar Americano (USD) => Boliviano Boliviano (BOB)"));
        conversions.add(new Conversion("BOB", "USD", "Boliviano Boliviano (BOB) => Dólar Americano (USD)"));
        conversions.add(new Conversion("USD", "CLP", "Dólar Americano (USD) => Peso Chileno (CLP)"));
        conversions.add(new Conversion("CLP", "USD", "Peso Chileno (CLP) => Dólar Americano (USD)"));
        conversions.add(new Conversion("USD", "COP", "Dólar Americano (USD) => Peso Colombiano (COP)"));
        conversions.add(new Conversion("COP", "USD", "Peso Colombiano (COP) => Dólar Americano (USD)"));
        return conversions;
    }

    public List<Conversion> getAvailableConversions() {
        return new ArrayList<>(availableConversions);
    }

    public String convertCurrency(int optionIndex, double amount) throws IOException, InterruptedException {
        if (optionIndex < 0 || optionIndex >= availableConversions.size()) {
            throw new IllegalArgumentException("Opção inválida");
        }

        Conversion conversion = availableConversions.get(optionIndex);
        String fromCurrency = conversion.getFromCurrency();
        String toCurrency = conversion.getToCurrency();

        System.out.println("Processando...");

        ExchangeRateResponse response = exchangeRateService.getExchangeRates(fromCurrency);
        Double rate = response.getRate(toCurrency);

        if (rate == null) {
            throw new RuntimeException("Taxa de câmbio não encontrada para " + toCurrency);
        }

        double convertedAmount = amount * rate;

        // Salvar no histórico
        ConversionHistory historyEntry = new ConversionHistory(fromCurrency, toCurrency, amount, convertedAmount, rate);
        conversionHistory.add(historyEntry);

        return String.format("O valor de %s %s corresponde a %s %s.",
                getCurrencySymbol(fromCurrency) + " " + decimalFormat.format(amount),
                fromCurrency,
                getCurrencySymbol(toCurrency) + " " + decimalFormat.format(convertedAmount),
                toCurrency);
    }

    public List<ConversionHistory> getConversionHistory() {
        return new ArrayList<>(conversionHistory);
    }

    public void showHistory() {
        if (conversionHistory.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
            return;
        }

        System.out.println("\n=== HISTÓRICO DE CONVERSÕES ===");
        for (int i = conversionHistory.size() - 1; i >= Math.max(0, conversionHistory.size() - 10); i--) {
            System.out.println((conversionHistory.size() - i) + ". " + conversionHistory.get(i));
        }
        System.out.println("===============================");
    }

    private String getCurrencySymbol(String currency) {
        return switch (currency) {
            case "USD" -> "US$";
            case "BRL" -> "R$";
            case "EUR" -> "€";
            case "ARS" -> "AR$";
            case "BOB" -> "Bs";
            case "CLP" -> "CL$";
            case "COP" -> "CO$";
            default -> currency;
        };
    }
}
