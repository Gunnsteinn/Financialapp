package com.financialapp.repository;

import com.financialapp.model.Currency;
import com.financialapp.util.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Repository("icbcCrawler")
public class ICBCCrawlerImp implements ICBCCrawler {
    private static List<Currency> currency;

    public List<Currency> findICBCCurrency() {
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
            currency.add(new Currency("DOLAR", StringUtils.stringToDoubleNumber(jsonBI.getString("buyRate")), StringUtils.stringToDoubleNumber(jsonBI.getString("sellRate"))));

            return currency;
        }catch (Exception e){
            currency.add(new Currency("Dolar",0.0,0.0));
            currency.add(new Currency("Euro",0.0,0.0));

            return currency;
        }
    }

}
