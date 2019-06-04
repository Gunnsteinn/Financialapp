package com.financialapp.repository;

import java.util.List;
import com.financialapp.model.Currency;

public interface GenericCrawler {
    List<Currency> findCurrency();
}
