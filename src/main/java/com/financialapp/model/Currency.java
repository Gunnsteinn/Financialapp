package com.financialapp.model;

public class Currency {

    String typeOfCurrency;
    Double buyRate;
    Double sellRate;

    public Currency(String typeOfCurrency, Double buyRate, Double sellRate) {
        this.typeOfCurrency = typeOfCurrency;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public String getTypeOfCurrency() {
        return typeOfCurrency;
    }

    public void setTypeOfCurrency(String typeOfCurrency) {
        this.typeOfCurrency = typeOfCurrency;
    }

    public Double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(Double buyRate) {
        this.buyRate = buyRate;
    }

    public Double getSellRate() {
        return sellRate;
    }

    public void setSellRate(Double sellRate) {
        this.sellRate = sellRate;
    }
}