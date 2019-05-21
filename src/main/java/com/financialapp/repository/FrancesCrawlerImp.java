package com.financialapp.repository;

import com.financialapp.model.Currency;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("francesCrawler")
public class FrancesCrawlerImp implements FrancesCrawler {
    private static List<Currency> currency;

    public List<Currency> findFrancesCurrency(){
        return this.CrawlerFrancesCurrency();
    }

    private List<Currency> CrawlerFrancesCurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            String webPage = "https://hb.bbv.com.ar/fnet/mod/inversiones/NL-dolareuro.jsp";
            String document3 = Jsoup.connect(webPage).get().html();
            Document doc3 = Jsoup.parse(document3);
            JSONObject jsonParentObject3 = new JSONObject();
            for (Element table : doc3.select("tbody")) {
                for (Element row : table.select("tr")) {
                    JSONObject jsonObject = new JSONObject();
                    Elements tds = row.select("td");
                    if (tds.size() > 0){
                        String type = tds.get(0).text();
                        String buy = tds.get(1).text();
                        String sell = tds.get(2).text();
                        jsonObject.put("buy", buy);
                        jsonObject.put("sell", sell);
                        currency.add(new Currency(type,buy,sell));
                        jsonParentObject3.put(type,jsonObject);
                    }
                }
            }

            return currency;
        }catch (Exception e){
            currency.add(new Currency("Dolar","00","00"));
            currency.add(new Currency("Euro","00","00"));

            return currency;
        }
    }
}
