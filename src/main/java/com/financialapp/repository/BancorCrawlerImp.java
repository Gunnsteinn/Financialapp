package com.financialapp.repository;

import com.financialapp.model.Currency;
import com.financialapp.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("bancorCrawler")
public class BancorCrawlerImp implements GenericCrawler {

    final static String requestUrl = "https://www.bancor.com.ar/718_APP//umbraco/api/Currency/GetCurrencyExchange";
    private JSONObject jsonEURC, jsonUSDC, jsonEURV, jsonUSDV;

    @Value("${app.currency.default-sell-tax-percentage}")
    private Double sellTaxPercentage;

    public List<Currency> findCurrency() {
        return this.CrawlerBancorCurrency();
    }

    private List<Currency> CrawlerBancorCurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            Connection.Response response = Jsoup.connect(requestUrl)
                    .userAgent("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                    .validateTLSCertificates(false)
                    .ignoreContentType(true)
                    .execute();

            Document doc = Jsoup.parse(response.parse().html());
            Elements table = doc.select("body");
            //System.out.println(table.get(0).text());

            JSONArray jsonArray = new JSONArray(table.get(0).text());
            jsonArray.forEach(item -> {
                JSONObject obj = (JSONObject) item;
                switch ((obj.getString("CurrencyCode") + obj.getString("OperationCode")).toUpperCase()) {
                    case "USDMCC":
                        jsonUSDC = obj;
                        break;
                    case "USDMCV":
                        jsonUSDV = obj;
                        break;
                    case "EURMCC":
                        jsonEURC = obj;
                        break;
                    case "EURMCV":
                        jsonEURV = obj;
                        break;
                }
            });

            if (jsonUSDC != null && jsonUSDV != null) {
                currency.add(new Currency("DOLAR", "USD", StringUtils.stringToDoubleNumber(((JSONObject) jsonUSDC).getString("Value")),
                        StringUtils.stringToDoubleNumber(((JSONObject) jsonUSDV).getString("Value")),
                        sellTaxPercentage));
            }

            if (jsonEURC != null && jsonEURV != null) {
                currency.add(new Currency("EURO", "EUR", StringUtils.stringToDoubleNumber(((JSONObject) jsonEURC).getString("Value")),
                        StringUtils.stringToDoubleNumber(((JSONObject) jsonEURV).getString("Value")),
                        sellTaxPercentage));
            }

            return currency;
        } catch (Exception e) {
            currency = new ArrayList<>();
            currency.add(new Currency("DOLAR", "USD", 0.0, 0.0, sellTaxPercentage));
            currency.add(new Currency("EURO", "EUR", 0.0, 0.0, sellTaxPercentage));

            return currency;
        }
    }
}
