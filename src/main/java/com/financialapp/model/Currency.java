package com.financialapp.model;

public class Currency {

    String type;
    String code;
    Double buyRate;
    Double sellRate;

    public Currency(String type,String code, Double buyRate, Double sellRate) {
        this.type = type;
        this.code = code;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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