package models;

public class Conversion {
    private final String fromCurrency;
    private final String toCurrency;
    private final String description;

    public Conversion(String fromCurrency, String toCurrency, String description) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.description = description;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public String getDescription() {
        return description;
    }
}
