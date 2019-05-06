package com.financialapp.service;

import com.financialapp.model.Currency;
import com.financialapp.repository.CurrencyService;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("currencyService")
public class CurrencyServiceImp implements CurrencyService{

    private static List<Currency> currencies;

    public List<Currency> findAllCurrency() {
        try {
            String webPageSR = "https://banco.santanderrio.com.ar/exec/cotizacion/index.jsp";
            String webPage1 = "https://ebankpersonas.bancopatagonia.com.ar/eBanking/usuarios/cotizacionMonedaExtranjera.htm"
            String webPage2 = "https://www.bancogalicia.com/banca/online/web/Personas/ProductosyServicios/Cotizador"
            String webPage3 = "http://www.bna.com.ar/Personas" 
            String webPage4 = "https://www.bbvafrances.com.ar/personas/inversiones/cotizaciones/cotizacion-moneda-extranjera/" 
            List<Currency> currencies = new ArrayList<Currency>();
            String document = Jsoup.connect(webPageSR).get().html();
            Document doc = Jsoup.parse(document);
            JSONObject jsonParentObject = new JSONObject();
            for (Element table : doc.select("table")) {
                for (Element row : table.select("tr")) {
                    JSONObject jsonObject = new JSONObject();
                    Elements tds = row.select("td");
                    if (tds.size() > 0){
                        String type = tds.get(0).text();
                        String buy = tds.get(1).text();
                        String sell = tds.get(2).text();
                        jsonObject.put("buy", buy);
                        jsonObject.put("sell", sell);
                        currencies.add(new Currency(type,"  ",buy,sell));
                        jsonParentObject.put(type,jsonObject);
                    }
                }
            }
            return currencies;
        }catch (Exception e){
            currencies.add(new Currency("Dolar","USD","00","00"));
            currencies.add(new Currency("Euro","EUR","00","00"));
            return currencies;
        }
    }

}
