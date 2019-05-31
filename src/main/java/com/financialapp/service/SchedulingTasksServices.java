package com.financialapp.service;

import com.financialapp.model.Currency;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class SchedulingTasksServices {

    @Autowired
    CurrencyService currencyService;

    private static final Logger log = LoggerFactory.getLogger(SchedulingTasksServices.class);

    @Scheduled(fixedRate = 60000)
    public void reportCurrencyCrawler() {
        log.info(currencyService.resetAllEntries());
        currencyService.findAllCurrency();
    }

    @Scheduled(fixedRate = 600)
    public void reportCurrency() {
        try {
            List<Currency> result = currencyService.findParticularCurrency("FM");

            String UrlFormater = " https://api.telegram.org/bot825328580:AAG1acLV0No7awRgIurNwjWDcdq0WrJjjwg/sendMessage?chat_id=591887299&text=" + result;
            InputStream dolarBI = new URL(UrlFormater).openStream();
            BufferedReader rdBI = new BufferedReader(new InputStreamReader(dolarBI, Charset.forName("UTF-8")));
            StringBuilder sbBI = new StringBuilder();
            int cpBI;
            while ((cpBI = rdBI.read()) != -1) {
                sbBI.append((char) cpBI);
            }

            String jsonTextBI = sbBI.toString();
            JSONObject jsonBI = new JSONObject(jsonTextBI);
            if(!jsonBI.getBoolean("ok")){
                reportCurrency();
            }
        }catch (Exception e){}
    }
}
