package com.financialapp.repository;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import com.financialapp.model.Mae;
import com.financialapp.model.MaeTotalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Repository("currencyRepository")
public class CurrencyRepositoryImp implements CurrencyRepository {

    private List<Currency> currency;
    private List<Bank> allCurrencies;
    private List<Mae> mae;
    private List<MaeTotalData> maeTotalData;

    @Autowired
    @Qualifier("francesCrawler")
    GenericCrawler francesCrawler;

    @Autowired
    @Qualifier("galiciaCrawler")
    GenericCrawler galiciaCrawler;

    @Autowired
    @Qualifier("icbcCrawler")
    GenericCrawler icbcCrawler;

    @Autowired
    @Qualifier("nacionCrawler")
    GenericCrawler nacionCrawler;

    @Autowired
    @Qualifier("patagoniaCrawler")
    GenericCrawler patagoniaCrawler;

    @Autowired
    @Qualifier("santanderRioCrawler")
    GenericCrawler santanderRioCrawler;

    @Autowired
    @Qualifier("bancorCrawler")
    GenericCrawler bancorCrawler;

    @Autowired
    @Qualifier("supervielleCrawler")
    GenericCrawler supervielleCrawler;

    @Autowired
    @Qualifier("provinciaBACrawler")
    GenericCrawler provinciaBACrawler;

    @Autowired
    @Qualifier("maeCrawler")
    MaeCrawler maeCrawler;

    public List<MaeTotalData> findMaeCrawler() {
        try {
            return maeCrawler.findMae();
        } catch (Exception e) {
            mae.add(new Mae("CAM1", "UST / ART 000", "0", 0.0, ""));
            maeTotalData.add(new MaeTotalData("", mae));
            return maeTotalData;
        }
    }

    public List<Currency> findCrawlerCurrency(String bank) {
        try {
            switch (bank) {
                case "BS":
                    return santanderRioCrawler.findCurrency();
                case "BP":
                    return patagoniaCrawler.findCurrency();
                case "BF":
                    return francesCrawler.findCurrency();
                case "BN":
                    return nacionCrawler.findCurrency();
                case "BI":
                    return icbcCrawler.findCurrency();
                case "BG":
                    return galiciaCrawler.findCurrency();
                case "BV":
                    return supervielleCrawler.findCurrency();
                case "BB":
                    return provinciaBACrawler.findCurrency();
                case "BC":
                    return bancorCrawler.findCurrency();
                default:
                    currency.add(new Currency("DOLAR", "USD", 0.0, 0.0, 0.0));
                    currency.add(new Currency("EURO", "EUR", 0.0, 0.0, 0.0));
                    return currency;
            }
        } catch (Exception e) {
            currency.add(new Currency("DOLAR", "USD", 0.0, 0.0, 0.0));
            currency.add(new Currency("EURO", "EUR", 0.0, 0.0, 0.0));
            return currency;
        }
    }

    List<Callable<Bank>> callables = Arrays.asList(
            () -> new Bank("Banco de la Nación Argentina", nacionCrawler.findCurrency()),
            () -> new Bank("Banco Santander Rio", santanderRioCrawler.findCurrency()),
            () -> new Bank("Banco BBVA", francesCrawler.findCurrency()),
            () -> new Bank("Banco Galicia", galiciaCrawler.findCurrency()),
            () -> new Bank("Banco Patagonia", patagoniaCrawler.findCurrency()),
            () -> new Bank("Banco ICBC", icbcCrawler.findCurrency()),
            () -> new Bank("Banco SuperVielle", supervielleCrawler.findCurrency()),
            () -> new Bank("Banco Provincia de Buenos Aires", provinciaBACrawler.findCurrency()),
            () -> new Bank("Banco Provincia de Córdoba", bancorCrawler.findCurrency())
    );

    public List<Bank> findAllCrawlerCurrency() {
        try {
            Long start = System.currentTimeMillis() / 1000;
            List<Bank> allCurrencies = new ArrayList<Bank>();
            ExecutorService executor = Executors.newWorkStealingPool();
            executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(item -> allCurrencies.add(item));
            System.out.println("Finish: " + (System.currentTimeMillis() / 1000 - start));
            return allCurrencies;
        } catch (Exception e) {
            currency.add(new Currency("DOLAR", "USD", 0.0, 0.0, 0.0));
            currency.add(new Currency("EURO", "EUR", 0.0, 0.0, 0.0));

            List<Bank> allCurrencies = new ArrayList<Bank>();
            allCurrencies.add(new Bank("BancoSantander Rio", currency));
            allCurrencies.add(new Bank("Banco de la Nación Argentina", currency));
            allCurrencies.add(new Bank("Banco Patagonia", currency));
            allCurrencies.add(new Bank("Banco BBVA", currency));
            allCurrencies.add(new Bank("Banco Galicia", currency));
            allCurrencies.add(new Bank("Banco ICBC", currency));
            allCurrencies.add(new Bank("Banco SuperVille", currency));
            allCurrencies.add(new Bank("Banco Provincia", currency));

            return allCurrencies;
        }
    }
}

