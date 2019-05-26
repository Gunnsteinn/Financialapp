package com.financialapp.repository;

import com.financialapp.model.Currency;
import com.financialapp.util.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("francesCrawler")
public class FrancesCrawlerImp implements GenericCrawler {

    public List<Currency> findCurrency(){
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
                        String code = tds.get(0).text();
                        String buy = tds.get(1).text();
                        String sell = tds.get(2).text();
                        jsonObject.put("buy", buy);
                        jsonObject.put("sell", sell);
                        currency.add(new Currency( StringUtils.stringTypeNormalize(type), StringUtils.stringCodeNormalize(code), StringUtils.stringToDoubleNumber(buy), StringUtils.stringToDoubleNumber(sell)));
                        jsonParentObject3.put(type,jsonObject);
                    }
                }
            }

            return currency;
        }catch (Exception e){
            currency.add(new Currency("DOLAR","USD",0.0,0.0));
            currency.add(new Currency("EURO","EUR",0.0,0.0));
            return currency;
        }
    }
}
