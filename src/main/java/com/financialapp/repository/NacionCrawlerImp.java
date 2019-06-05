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

@Repository("nacionCrawler")
public class NacionCrawlerImp implements GenericCrawler {

    public List<Currency> findCurrency(){
        return this.CrawlerNacionCurrency();
    }

    private List<Currency> CrawlerNacionCurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            String webPage = "http://www.bna.com.ar/Personas";
            String document4 = Jsoup.connect(webPage).get().html();
            Document doc4 = Jsoup.parse(document4);
            JSONObject jsonParentObject4 = new JSONObject();
            for (Element table : doc4.select("div#billetes")) {
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
                        Double buyFix = 0.0;
                        Double sellFix = 0.0;
                        if (StringUtils.stringTypeNormalize(type).equalsIgnoreCase("REAL")){
                            buyFix = StringUtils.stringToDoubleNumber(buy)/100;
                            sellFix = StringUtils.stringToDoubleNumber(sell)/100;
                        }else{
                            buyFix = StringUtils.stringToDoubleNumber(buy);
                            sellFix = StringUtils.stringToDoubleNumber(sell);
                        }
                        currency.add(new Currency( StringUtils.stringTypeNormalize(type), StringUtils.stringCodeNormalize(code), buyFix, sellFix));
                        jsonParentObject4.put(StringUtils.stringTypeNormalize(type),jsonObject);
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

