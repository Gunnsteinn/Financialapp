package com.financialapp.service;

import com.financialapp.model.Currency;
import com.financialapp.repository.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("currencyService")
public class CurrencyServiceImp implements CurrencyService {
    private static List<Currency> currencies;

    static{
        currencies= populateDummyCurrencies();
    }
    public List<Currency> findAllCurrency() {
        return currencies;
    }

    private static List<Currency> populateDummyCurrencies(){

        List<Currency> currencies = new ArrayList<Currency>();
        currencies.add(new Currency("Dolar","USD",43,45));
        currencies.add(new Currency("Euro","EUR",48,50));

        return currencies;
    }
}
