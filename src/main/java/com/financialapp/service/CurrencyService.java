package com.financialapp.service;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;

import java.util.List;

public interface CurrencyService {

    List<Bank> findAllCurrency();

    String resetAllEntries();

    List<Bank> findOpenCurrencies();

    String resetOpenCurrencies();

    List<Currency> findParticularCurrency(String bank);

    Double findLastMaePrice();

    String resetLastMaePrice();

}
