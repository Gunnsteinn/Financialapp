package com.financialapp.repository;

import com.financialapp.model.Currency;
import java.util.List;

public interface CurrencyRepository {

    List<Currency> findCrawlerCurrency(String bank);

}
