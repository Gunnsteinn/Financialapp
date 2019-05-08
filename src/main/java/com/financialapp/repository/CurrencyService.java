package com.financialapp.repository;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import java.util.List;

public interface CurrencyService {

    List<Bank> findAllCurrency();

    List<Currency> findParticularCurrency(String bank);

}
