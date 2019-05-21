package com.financialapp.repository;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("currencyRepository")
public class CurrencyRepositoryImp implements CurrencyRepository{

    private static List<Currency> currency;
    private static List<Bank> allCurrencies;

    @Autowired
    FrancesCrawler francesCrawler;

    @Autowired
    GaliciaCrawler galiciaCrawler;

    @Autowired
    ICBCCrawler icbcCrawler;

    @Autowired
    NacionCrawler nacionCrawler ;

    @Autowired
    PatagoniaCrawler patagoniaCrawler ;

    @Autowired
    SantanderRioCrawler santanderRioCrawler ;

    public List<Currency> findCrawlerCurrency(String bank) {
        try {
            switch (bank) {
                case "BS":
                    return santanderRioCrawler.findSantanderRioCurrency();
                case "BP":
                    return patagoniaCrawler.findPatagoniaCurrency();
                case "BF":
                    return francesCrawler.findFrancesCurrency();
                case "BN":
                    return nacionCrawler.findNacionCurrency();
                case "BI":
                    return icbcCrawler.findICBCCurrency();
                case "BG":
                    return galiciaCrawler.findGaliciaCurrency();
                default:
                    currency.add(new Currency("Dolar","00","00"));
                    currency.add(new Currency("Euro","00","00"));
                    return currency;
            }
        }catch (Exception e){
            currency.add(new Currency("Dolar","00","00"));
            currency.add(new Currency("Euro","00","00"));
            return currency;
        }
    }
}
