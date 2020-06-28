package com.financialapp.repository;

import com.financialapp.model.Currency;
import com.financialapp.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Repository("provinciaBACrawler")
public class ProvinciaBACrawlerImp implements GenericCrawler {
    @Value("${app.currency.default-sell-tax-percentage}")
    private Double sellTaxPercentage;

    public List<Currency> findCurrency() {
        return this.CrawlerProvinciaBACurrency();
    }

    private List<Currency> CrawlerProvinciaBACurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            InputStream dolarBI = new URL(" https://www.bancoprovincia.com.ar/Principal/Dolar").openStream();
            BufferedReader rdBI = new BufferedReader(new InputStreamReader(dolarBI, Charset.forName("UTF-8")));
            StringBuilder sbBI = new StringBuilder();
            int cpBI;
            while ((cpBI = rdBI.read()) != -1) {
                sbBI.append((char) cpBI);
            }

            String jsonTextBI = sbBI.toString();
            String replaceStringBI = jsonTextBI.replaceAll("\\[", "");
            replaceStringBI = replaceStringBI.replaceAll("\\]", "");
            String[] arry = replaceStringBI.split(",");
            currency.add(new Currency("DOLAR", "USD",
                    StringUtils.stringToDoubleNumber(arry[0]),
                    StringUtils.stringToDoubleNumber(arry[1]),
                    sellTaxPercentage));

            return currency;
        } catch (Exception e) {
            currency.add(new Currency("DOLAR", "USD", 0.0, 0.0, sellTaxPercentage));

            return currency;
        }
    }
}
