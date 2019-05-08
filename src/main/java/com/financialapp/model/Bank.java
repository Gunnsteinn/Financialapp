package com.financialapp.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    String bank;
    List<Currency> exchange;

    public Bank(String bank, List<Currency> exchange) {
        this.bank = bank;
        this.exchange = exchange;
    }


    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public List<Currency> getExchange() {
        return exchange;
    }

    public void setExchange(List<Currency> exchange) {
        this.exchange = exchange;
    }

}
