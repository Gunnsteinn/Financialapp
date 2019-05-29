package com.financialapp.service;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import com.financialapp.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service("currencyService")
public class CurrencyServiceImp implements CurrencyService{

    private List<Currency> currency;
    private List<Bank> allCurrencies;
    private static final Logger log = LoggerFactory.getLogger(CurrencyServiceImp.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Currency> findParticularCurrency(String bank) {
        try {
            return currencyRepository.findCrawlerCurrency(bank);
        }catch (Exception e){
            currency.add(new Currency("DOLAR","USD",0.0,0.0));
            currency.add(new Currency("EURO","EUR",0.0,0.0));
            return currency;
        }
    }

    @Cacheable("bank")
    public List<Bank> findAllCurrency() {
        try {
            log.info("Time to Crawler. {}", dateFormat.format(new Date()));
            return currencyRepository.findAllCrawlerCurrency();
        }catch (Exception e){
            currency.add(new Currency("DOLAR","USD",0.0,0.0));
            currency.add(new Currency("EURO","EUR",0.0,0.0));

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

    @CacheEvict(cacheNames="bank", allEntries=true)
    public String resetAllEntries() {
        return "Reset Cache " + dateFormat.format(new Date());
    }
}