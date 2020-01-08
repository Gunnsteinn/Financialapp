package com.financialapp.repository;

import com.financialapp.model.Mae;
import com.financialapp.model.MaeTotalData;
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
public class MaeCrawlerImp implements MaeCrawler {

    public List<MaeTotalData> findMae(){
        return this.CrawlerMAECurrencies();
    }

    private List<MaeTotalData> CrawlerMAECurrencies() {
        List<Mae> maeInfo = new ArrayList<Mae>();
        List<MaeTotalData> maeTotalData= new ArrayList<MaeTotalData>();
        try {
             String webPage = "https://servicios.mae.com.ar/mercados/Forex/Default.aspx";
            //String document3 = Jsoup.connect(webPage).get().html();
            //Document doc3 = Jsoup.parse(document3);
            String document = Jsoup.connect(webPage).get().html();
            Document doc = Jsoup.parse(document);
            JSONObject jsonParentObject = new JSONObject();
            for (Element table : doc.select("table.tabladatossmall")) {
                for (Element row : table.select("tr")) {
                    JSONObject jsonObject = new JSONObject();
                    Elements tds = row.select("td");
                    //if (tds.size() > 0 && tds.get(0).text().contains("CAM1") && tds.get(1).text().contains("UST / ART 000")) {
                    if (tds.size() > 0 ) {
                        String wheel = tds.get(0).text();
                        String appliance = tds.get(1).text();
                        String negotiatedAmount = tds.get(2).text();
                        String sell = tds.get(3).text();
                        String hour = tds.get(4).text();
                        jsonObject.put("wheel", wheel);
                        jsonObject.put("appliance", appliance);
                        jsonObject.put("negotiatedAmount", negotiatedAmount);
                        jsonObject.put("sell", sell);
                        jsonObject.put("hour", hour);
                        Elements info = doc.select("img#ctl00_ContentPlaceHolder1_CHEvolucionForex");
                        maeInfo.add(new Mae(wheel, appliance, negotiatedAmount, StringUtils.stringToDoubleNumber(sell),hour));
                        jsonParentObject.put(wheel,jsonObject);
                    }
                }
            }

            Elements info = doc.select("img#ctl00_ContentPlaceHolder1_CHEvolucionForex");
            String data = info.toString();
            String requiredString = data.substring(data.indexOf("src=") + 5, data.indexOf("\" alt"));
            //currency.add(new Currency("DOLAR","USD",0.0,StringUtils.stringToDoubleNumber(info.get(0).text())));
            maeTotalData.add(new MaeTotalData(requiredString,maeInfo));
            return maeTotalData;
        }catch (Exception e){
            maeInfo.add(new Mae("CAM1", "UST / ART 000", "0", 0.0,""));
            maeTotalData.add(new MaeTotalData("",maeInfo));
            return maeTotalData;
        }
    }
}
