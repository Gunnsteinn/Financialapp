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

@Repository("maeCrawler")
public class MAECrawlerImp implements GenericCrawler {

    public List<Currency> findCurrency(){
        return this.CrawlerMAECurrency();
    }

    private List<Currency> CrawlerMAECurrency() {
        List<Currency> currency = new ArrayList<Currency>();
        try {
            String webPage = "http://www.mae.com.ar/mercados/forex/default.aspx";
            String document3 = Jsoup.connect(webPage).get().html();
            Document doc3 = Jsoup.parse(document3);
            Elements info = doc3.select("span#ctl00_ContentPlaceHolder1_GVRFUltPreciosForex_ctl10_LBLUltPre");
            currency.add(new Currency("DOLAR","USD",0.0,StringUtils.stringToDoubleNumber(info.get(0).text())));
            return currency;
        }catch (Exception e){
            currency.add(new Currency("DOLAR","USD",0.0,0.0));
            return currency;
        }
    }
}
