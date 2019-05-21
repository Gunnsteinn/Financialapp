package com.financialapp.repository;

import com.financialapp.model.Currency;
import java.util.List;

public interface ICBCCrawler {
    List<Currency> findICBCCurrency();
}
