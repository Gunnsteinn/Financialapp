package com.financialapp.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Currency {

    String type;
    String code;
    Double buyRate;
    Double sellRate;
    Double sellTaxPercentage;

    public Currency(String type, String code, Double buyRate, Double sellRate, Double sellTaxPercentage) {
        this.type = type;
        this.code = code;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.sellTaxPercentage = sellTaxPercentage;
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

    public Double getSellRateWithTax() {
        BigDecimal rate =BigDecimal.valueOf(this.sellRate + (this.sellRate * this.sellTaxPercentage));
        return rate.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
