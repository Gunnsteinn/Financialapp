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

@Repository("galiciaCrawler")
public class GaliciaCrawlerImp implements GenericCrawler {

    public List<Currency> findCurrency() {
        return this.CrawlerGaliciaCurrency();
    }

    private List<Currency> CrawlerGaliciaCurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            InputStream dolar = new URL("https://www.bancogalicia.com/cotizacion/cotizar?currencyId=02&quoteType=SU&quoteId=999").openStream();
            BufferedReader rd1 = new BufferedReader(new InputStreamReader(dolar, Charset.forName("UTF-8")));
            StringBuilder sb1 = new StringBuilder();
            int cp1;
                            while ((cp1 = rd1.read()) != -1) {
                sb1.append((char) cp1);
            }

            String jsonText = sb1.toString();
            String replaceString = jsonText.replaceAll("\\bbuy\\b", "buyRate");
            replaceString = replaceString.replaceAll("\\bsell\\b", "sellRate");
            JSONObject json = new JSONObject(replaceString);
                            currency.add(new Currency("DOLAR","USD", StringUtils.stringToDoubleNumber(json.getString("buyRate")),StringUtils.stringToDoubleNumber(json.getString("sellRate"))));

            InputStream euro = new URL("https://www.bancogalicia.com/cotizacion/cotizar?currencyId=98&quoteType=SU&quoteId=999").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(euro, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp2;
                            while ((cp2 = rd.read()) != -1) {
                sb.append((char) cp2);
            }

            String jsonText1 = sb.toString();
            String replaceString1 = jsonText1.replaceAll("\\bbuy\\b", "buyRate");
            replaceString1 = replaceString1.replaceAll("\\bsell\\b", "sellRate");
            JSONObject json1 = new JSONObject(replaceString1);
                            currency.add(new Currency("EURO","EUR",StringUtils.stringToDoubleNumber(json1.getString("buyRate")),StringUtils.stringToDoubleNumber(json1.getString("sellRate"))));
            return currency;
        }catch (Exception e){
            currency.add(new Currency("DOLAR","USD",0.0,0.0));
            currency.add(new Currency("EURO","EUR",0.0,0.0));
            return currency;
        }
    }
}
