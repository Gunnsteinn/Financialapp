package com.financialapp.model;

public class Currency {

    String typeOfCurrency;
    String code;
    float buyRate;
    float sellRate;

    public Currency(String typeOfCurrency, String code, float buyRate, float sellRate) {
        this.typeOfCurrency = typeOfCurrency;
        this.code = code;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public String getTypeOfCurrency() {
        return typeOfCurrency;
    }

    public void setTypeOfCurrency(String typeOfCurrency) {
        this.typeOfCurrency = typeOfCurrency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(float buyRate) {
        this.buyRate = buyRate;
    }

    public float getSellRate() {
        return sellRate;
    }

    public void setSellRate(float sellRate) {
        this.sellRate = sellRate;
    }
}