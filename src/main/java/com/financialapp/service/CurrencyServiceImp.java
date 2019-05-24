package com.financialapp.service;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import com.financialapp.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;


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
            currency.add(new Currency("Dolar",0.0,0.0));
            currency.add(new Currency("Euro",0.0,0.0));
            return currency;
        }
    }

    List<Callable<Bank>> callables = Arrays.asList(
            () -> new Bank("Banco de la Nación Argentina",findParticularCurrency("BN")),
            () -> new Bank("Santander Rio",findParticularCurrency("BS")),
            () -> new Bank("Banco BBVA Francés",findParticularCurrency("BF")),
            () -> new Bank("Banco Galicia",findParticularCurrency("BG")),
            () -> new Bank("Banco Patagonia",findParticularCurrency("BP")),
            () -> new Bank("Banco ICBC",findParticularCurrency("BI"))
    );

    public List<Bank> findAllCurrency() {
        try {
            Long start = System.currentTimeMillis()/1000;
            List<Bank> allCurrencies = new ArrayList<Bank>();
            ExecutorService executor = Executors.newWorkStealingPool();
            executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }
                        catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(item -> allCurrencies.add(item));
            System.out.println("Finish: " + (System.currentTimeMillis()/1000-start));
            return allCurrencies;
        }catch (Exception e){
            currency.add(new Currency("Dolar",0.0,0.0));
            currency.add(new Currency("Euro",0.0,0.0));

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