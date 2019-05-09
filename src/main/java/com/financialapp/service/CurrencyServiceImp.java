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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
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
                case "BF":
                    webPage = "https://hb.bbv.com.ar/fnet/mod/inversiones/NL-dolareuro.jsp";
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
                    break;
                case "BN":
                    webPage = "http://www.bna.com.ar/Personas";
                    String document4 = Jsoup.connect(webPage).get().html();
                    Document doc4 = Jsoup.parse(document4);
                    JSONObject jsonParentObject4 = new JSONObject();
                    for (Element table : doc4.select("div#billetes")) {
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
                                jsonParentObject4.put(type,jsonObject);
                            }
                        }
                    }
                    break;
                case "BG":
                    InputStream dolar = new URL("https://www.bancogalicia.com/cotizacion/cotizar?currencyId=02&quoteType=SU&quoteId=999").openStream();
                    BufferedReader rd1 = new BufferedReader(new InputStreamReader(dolar, Charset.forName("UTF-8")));
                    StringBuilder sb1 = new StringBuilder();
                    int cp1;
                    while ((cp1 = rd1.read()) != -1) {
                        sb1.append((char) cp1);
                    }

                    String jsonText = sb1.toString();
                    String replaceString = jsonText.replaceAll("\\bbuy\\b", "buyRate");
                    replaceString = replaceString.replaceAll("\\bsell\\b", "sellRate");
                    JSONObject json = new JSONObject(replaceString);
                    currency.add(new Currency("Dolar",json.getString("buyRate"),json.getString("sellRate")));

                    InputStream euro = new URL("https://www.bancogalicia.com/cotizacion/cotizar?currencyId=98&quoteType=SU&quoteId=999").openStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(euro, Charset.forName("UTF-8")));
                    StringBuilder sb = new StringBuilder();
                    int cp2;
                    while ((cp2 = rd.read()) != -1) {
                        sb.append((char) cp2);
                    }

                    String jsonText1 = sb.toString();
                    String replaceString1 = jsonText1.replaceAll("\\bbuy\\b", "buyRate");
                    replaceString1 = replaceString1.replaceAll("\\bsell\\b", "sellRate");
                    JSONObject json1 = new JSONObject(replaceString1);
                    currency.add(new Currency("Euro",json1.getString("buyRate"),json1.getString("sellRate")));

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
            List<Currency> currenciesBF = findParticularCurrency("BF");
            List<Currency> currenciesBN = findParticularCurrency("BN");
            List<Currency> currenciesBG = findParticularCurrency("BG");


            allCurrencies.add(new Bank("Santander Rio",currenciesBS));
            allCurrencies.add(new Bank("Banco de la Nación Argentina",currenciesBN));
            allCurrencies.add(new Bank("Banco Patagonia",currenciesBP));
            allCurrencies.add(new Bank("BBVA Francés",currenciesBF));
            allCurrencies.add(new Bank("Banco Galicia",currenciesBG));

            return allCurrencies;
        }catch (Exception e){
            currency.add(new Currency("Dolar","00","00"));
            currency.add(new Currency("Euro","00","00"));

            List<Bank> allCurrencies = new ArrayList<Bank>();
            allCurrencies.add(new Bank("Santander Rio",currency));
            allCurrencies.add(new Bank("Banco de la Nación Argentina",currency));
            allCurrencies.add(new Bank("Banco Patagonia",currency));
            allCurrencies.add(new Bank("BBVA Francés",currency));
            allCurrencies.add(new Bank("Banco Galicia",currency));

            return allCurrencies;
        }
    }

}