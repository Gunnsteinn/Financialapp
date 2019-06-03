package com.financialapp.repository;

import com.financialapp.model.Currency;
import com.financialapp.util.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository("bancorCrawler")
public class BancorCrawlerImp implements GenericCrawler{

    public List<Currency> findCurrency(){
        return this.CrawlerSantanderRioCurrency();
    }

    private List<Currency> CrawlerSantanderRioCurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            String webPage = "https://www.bancor.com.ar/718_APP//umbraco/api/Currency/GetCurrencyExchange";
            String document = Jsoup.connect(webPage).timeout(100000).validateTLSCertificates(false).get().html();

            String replaceStringBI = document.replaceAll("\\Value\\b", "buyRate");
            replaceStringBI = replaceStringBI.replaceAll("\\bvalorVenta\\b", "sellRate");
            JSONObject jsonBI = new JSONObject(replaceStringBI);
            currency.add(new Currency("DOLAR","USD", StringUtils.stringToDoubleNumber(jsonBI.getString("buyRate")), StringUtils.stringToDoubleNumber(jsonBI.getString("sellRate"))));

            return currency;


//            String document = Jsoup.connect(webPage)
//                    .cookie("citrix_ns_id", "/8QCg3iVEIqOxFMnJM4KZneea8Y0001")
//                    .get().html();

            //"CurrencyCode": "EUR",
            //        "OperationCode": "MCC",
            //        "Value": "50,8000"
        }catch (Exception e){
            currency.add(new Currency("DOLAR","USD",0.0,0.0));
            currency.add(new Currency("EURO","EUR",0.0,0.0));

            return currency;
        }


    }
}
