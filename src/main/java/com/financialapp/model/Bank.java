package com.financialapp.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    String bank;
    List<Currency> currencyList;

    public Bank(String bank, List<Currency> currencyList) {
        this.bank = bank;
        this.currencyList = currencyList;
    }


    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

}
