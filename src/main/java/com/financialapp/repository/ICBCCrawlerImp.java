package com.financialapp.repository;

import com.financialapp.model.Currency;
import com.financialapp.util.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Repository("icbcCrawler")
public class ICBCCrawlerImp implements GenericCrawler {
    @Value("${app.currency.default-sell-tax-percentage}")
    private Double sellTaxPercentage;

    public List<Currency> findCurrency() {
        return this.CrawlerICBCCurrency();
    }

    private List<Currency> CrawlerICBCCurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            InputStream dolarBI = new URL("https://www.icbc.com.ar/ICBC_CotizacionMonedaWEB/cotizacion/dolar").openStream();
            BufferedReader rdBI = new BufferedReader(new InputStreamReader(dolarBI, Charset.forName("UTF-8")));
            StringBuilder sbBI = new StringBuilder();
            int cpBI;
            while ((cpBI = rdBI.read()) != -1) {
                sbBI.append((char) cpBI);
            }

            String jsonTextBI = sbBI.toString();
            String replaceStringBI = jsonTextBI.replaceAll("\\bvalorCompra\\b", "buyRate");
            replaceStringBI = replaceStringBI.replaceAll("\\bvalorVenta\\b", "sellRate");
            JSONObject jsonBI = new JSONObject(replaceStringBI);
            currency.add(new Currency("DOLAR", "USD", StringUtils.stringToDoubleNumber(jsonBI.getString("buyRate")),
                    StringUtils.stringToDoubleNumber(jsonBI.getString("sellRate")),
                    sellTaxPercentage));

            return currency;
        } catch (Exception e) {
            currency.add(new Currency("DOLAR", "USD", 0.0, 0.0, sellTaxPercentage));

            return currency;
        }
    }

}
