package com.financialapp.repository;

import com.financialapp.model.Currency;
import com.financialapp.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;


import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Repository("bancorCrawler")
public class BancorCrawlerImp implements GenericCrawler{

    final static String requestUrl = "https://www.bancor.com.ar/718_APP//umbraco/api/Currency/GetCurrencyExchange";
    public List<Currency> findCurrency(){
        return this.CrawlerSantanderRioCurrency();
    }

    private List<Currency> CrawlerSantanderRioCurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try
        {
            Connection.Response response = Jsoup.connect(requestUrl)
                    .userAgent("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                    .validateTLSCertificates(false)
                    .ignoreContentType(true)
                    .execute();

            Document doc = Jsoup.parse(response.parse().html());
            Elements table = doc.select("body");
            System.out.println(table.get(0).text());
            JSONArray jsonArray = new JSONArray(table.get(0).text());

            Object jsonEURC = jsonArray.getJSONObject(0);
            Object jsonUSDC = jsonArray.getJSONObject(1);
            Object jsonEURV = jsonArray.getJSONObject(2);
            Object jsonUSDV = jsonArray.getJSONObject(3);

            currency.add(new Currency("DOLAR","USD", StringUtils.stringToDoubleNumber(((JSONObject) jsonUSDC).getString("Value")), StringUtils.stringToDoubleNumber(((JSONObject) jsonUSDV).getString("Value"))));
            currency.add(new Currency("EURO","EUR",StringUtils.stringToDoubleNumber(((JSONObject) jsonEURC).getString("Value")),StringUtils.stringToDoubleNumber(((JSONObject) jsonEURV).getString("Value"))));
            return currency;
        }
        catch (Exception e){
            currency.add(new Currency("DOLAR","USD",0.0,0.0));
            currency.add(new Currency("EURO","EUR",0.0,0.0));

            return currency;
        }


    }


}
