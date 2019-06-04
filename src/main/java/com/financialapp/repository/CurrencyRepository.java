package com.financialapp.repository;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import com.financialapp.model.MaeTotalData;

import java.util.List;

public interface CurrencyRepository {

    List<Currency> findCrawlerCurrency(String bank);

    List<Bank> findAllCrawlerCurrency();

    List<MaeTotalData> findMaeCrawler();

}
