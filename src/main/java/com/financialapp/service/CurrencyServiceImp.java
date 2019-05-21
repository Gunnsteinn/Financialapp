package com.financialapp.service;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import com.financialapp.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("currencyService")
public class CurrencyServiceImp implements CurrencyService{

    private static List<Currency> currency;
    private static List<Bank> allCurrencies;

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Currency> findParticularCurrency(String bank) {
        try {
            return currencyRepository.findCrawlerCurrency(bank);
        }catch (Exception e){
            currency.add(new Currency("Dolar","00","00"));
            currency.add(new Currency("Euro","00","00"));
            return currency;
        }
    }

    public List<Bank> findAllCurrency() {
        try {
            List<Bank> allCurrencies = new ArrayList<Bank>();
            List<Currency> currenciesBS = findParticularCurrency("BS");
            List<Currency> currenciesBP = findParticularCurrency("BP");
            List<Currency> currenciesBF = findParticularCurrency("BF");
            List<Currency> currenciesBN = findParticularCurrency("BN");
            List<Currency> currenciesBG = findParticularCurrency("BG");
            List<Currency> currenciesBI = findParticularCurrency("BI");


            allCurrencies.add(new Bank("Santander Rio",currenciesBS));
            allCurrencies.add(new Bank("Banco de la Nación Argentina",currenciesBN));
            allCurrencies.add(new Bank("Banco Patagonia",currenciesBP));
            allCurrencies.add(new Bank("BBVA Francés",currenciesBF));
            allCurrencies.add(new Bank("Banco Galicia",currenciesBG));
            allCurrencies.add(new Bank("Banco ICBC",currenciesBI));

            return allCurrencies;
        }catch (Exception e){
            currency.add(new Currency("Dolar","00","00"));
            currency.add(new Currency("Euro","00","00"));

            List<Bank> allCurrencies = new ArrayList<Bank>();
            allCurrencies.add(new Bank("Santander Rio",currency));
            allCurrencies.add(new Bank("Banco de la Nación Argentina",currency));
            allCurrencies.add(new Bank("Banco Patagonia",currency));
            allCurrencies.add(new Bank("BBVA Francés",currency));
            allCurrencies.add(new Bank("Banco Galicia",currency));
            allCurrencies.add(new Bank("Banco ICBC",currency));

            return allCurrencies;
        }
    }

}