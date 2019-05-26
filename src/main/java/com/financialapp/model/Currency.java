package com.financialapp.model;

public class Currency {

    String typeOfCurrency;
    String codeOfCurrency;
    Double buyRate;
    Double sellRate;

    public Currency(String typeOfCurrency,String codeOfCurrency, Double buyRate, Double sellRate) {
        this.typeOfCurrency = typeOfCurrency;
        this.codeOfCurrency = codeOfCurrency;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public String getTypeOfCurrency() {
        return typeOfCurrency;
    }

    public void setTypeOfCurrency(String typeOfCurrency) {
        this.typeOfCurrency = typeOfCurrency;
    }

    public String getCodeOfCurrency() {
        return codeOfCurrency;
    }

    public void setCodeOfCurrency(String codeOfCurrency) {
        this.codeOfCurrency = codeOfCurrency;
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