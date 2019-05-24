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

@Repository("patagoniaCrawler")
public class PatagoniaCrawlerImp implements PatagoniaCrawler {
    private static List<Currency> currency;

    public List<Currency> findPatagoniaCurrency(){
        return this.CrawlerPatagoniaCurrency();
    }

    private List<Currency> CrawlerPatagoniaCurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            String webPage = "https://ebankpersonas.bancopatagonia.com.ar/eBanking/usuarios/cotizacionMonedaExtranjera.htm";
            String document1 = Jsoup.connect(webPage).get().html();
            Document doc1 = Jsoup.parse(document1);
            JSONObject jsonParentObject1 = new JSONObject();
            for (Element table : doc1.select("tbody")) {
                for (Element row : table.select("tr")) {
                    JSONObject jsonObject = new JSONObject();
                    Elements tds = row.select("td");
                    if (tds.size() > 0){
                        String type = tds.get(0).text();
                        String buy = tds.get(1).text();
                        String sell = tds.get(2).text();
                        jsonObject.put("buy", buy);
                        jsonObject.put("sell", sell);
                        currency.add(new Currency( type, StringUtils.stringToDoubleNumber(buy), StringUtils.stringToDoubleNumber(sell)));
                        jsonParentObject1.put(type,jsonObject);
                    }
                }
            }

            return currency;
        }catch (Exception e){
            currency.add(new Currency("Dolar",0.0,0.0));
            currency.add(new Currency("Euro",0.0,0.0));

            return currency;
        }
    }
}
