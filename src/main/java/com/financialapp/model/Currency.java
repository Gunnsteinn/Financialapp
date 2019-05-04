package com.financialapp.model;

public class Currency {

    String typeOfCurrency;
    String code;
    String buyRate;
    String sellRate;

    public Currency(String typeOfCurrency, String code, String buyRate, String sellRate) {
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

    public String getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(String buyRate) {
        this.buyRate = buyRate;
    }

    public String getSellRate() {
        return sellRate;
    }

    public void setSellRate(String sellRate) {
        this.sellRate = sellRate;
    }
}