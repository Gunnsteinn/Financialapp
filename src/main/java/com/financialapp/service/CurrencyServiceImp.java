package com.financialapp.service;

import com.financialapp.model.Bank;
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

    private static List<Currency> currency;
    private static List<Bank> allCurrencies;

    public List<Currency> findParticularCurrency(String bank) {
        try {
            System.out.println(bank);

            String webPage;
            List<Currency> currency = new ArrayList<Currency>();
            switch (bank) {
                case "BS":
                    webPage = "https://banco.santanderrio.com.ar/exec/cotizacion/index.jsp";

                    String document = Jsoup.connect(webPage).get().html();
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
                                currency.add(new Currency(type,buy,sell));
                                jsonParentObject.put(type,jsonObject);
                            }
                        }
                    }
                    break;
                case "BP":
                    webPage = "https://ebankpersonas.bancopatagonia.com.ar/eBanking/usuarios/cotizacionMonedaExtranjera.htm";
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
                                currency.add(new Currency(type,buy,sell));
                                jsonParentObject1.put(type,jsonObject);
                            }
                        }
                    }
                    break;
                default:
                    webPage = "https://banco.santanderrio.com.ar/exec/cotizacion/index.jsp";
                    String document2 = Jsoup.connect(webPage).get().html();
                    Document doc2 = Jsoup.parse(document2);
                    JSONObject jsonParentObject2 = new JSONObject();
                    for (Element table : doc2.select("table")) {
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
                                jsonParentObject2.put(type,jsonObject);
                            }
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

    public List<Bank> findAllCurrency() {
        try {
            String webPageBS = "https://banco.santanderrio.com.ar/exec/cotizacion/index.jsp";
            String webPageBP = "https://ebankpersonas.bancopatagonia.com.ar/eBanking/usuarios/cotizacionMonedaExtranjera.htm";
            String webPageBG = "https://www.bancogalicia.com/banca/online/web/Personas/ProductosyServicios/Cotizador";
            String webPageBN = "http://www.bna.com.ar/Personas" ;
            String webPageBF = "https://www.bbvafrances.com.ar/personas/inversiones/cotizaciones/cotizacion-moneda-extranjera/";

            List<Bank> allCurrencies = new ArrayList<Bank>();
            List<Currency> currenciesBS = findParticularCurrency("BS");
            List<Currency> currenciesBP = findParticularCurrency("BP");
            allCurrencies.add(new Bank("Santander Rio",currenciesBS));
            allCurrencies.add(new Bank("Banco Patagonia",currenciesBP));

            return allCurrencies;
        }catch (Exception e){
            currency.add(new Currency("Dolar","00","00"));
            currency.add(new Currency("Euro","00","00"));

            List<Bank> allCurrencies = new ArrayList<Bank>();
            allCurrencies.add(new Bank("BS",currency));
            allCurrencies.add(new Bank("BP",currency));

            return allCurrencies;
        }
    }
}
