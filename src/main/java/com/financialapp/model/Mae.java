package com.financialapp.model;

public class Mae {

    String wheel;
    String appliance;
    String negotiatedAmount;
    Double sellRate;
    String hora;

    public Mae(String wheel, String appliance, String negotiatedAmount, Double sellRate, String hora) {
        this.wheel = wheel;
        this.appliance = appliance;
        this.negotiatedAmount = negotiatedAmount;
        this.sellRate = sellRate;
        this.hora = hora;
    }

    public String getWheel() {
        return wheel;
    }

    public void setWheel(String wheel) {
        this.wheel = wheel;
    }

    public String getAppliance() {
        return appliance;
    }

    public void setAppliance(String appliance) {
        this.appliance = appliance;
    }

    public String getNegotiatedAmount() {
        return negotiatedAmount;
    }

    public void setNegotiatedAmount(String negotiatedAmount) {
        this.negotiatedAmount = negotiatedAmount;
    }

    public Double getSellRate() {
        return sellRate;
    }

    public void setSellRate(Double sellRate) {
        this.sellRate = sellRate;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        hora = hora;
    }
}
