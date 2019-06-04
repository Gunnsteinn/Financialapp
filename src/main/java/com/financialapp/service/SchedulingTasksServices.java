package com.financialapp.service;

import com.financialapp.model.Currency;
import com.financialapp.model.Mae;
import com.financialapp.model.MaeTotalData;
import com.financialapp.repository.CurrencyRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;


@Component
public class SchedulingTasksServices {

    @Autowired
    CurrencyService currencyService;

    @Autowired
    CurrencyRepository currencyRepository;

    private static final Logger log = LoggerFactory.getLogger(SchedulingTasksServices.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void reportCurrencyCrawler() {
        log.info(currencyService.resetAllEntries());
        currencyService.findAllCurrency();
    }


    //* "0 0 * * * *" = the top of every hour of every day.
    //* "*/10 * * * * *" = every ten seconds.
    //* "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
    //* "0 0 8,10 * * *" = 8 and 10 o'clock of every day.
    //* "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
    //* "0 0/30 9-17 * * MON-SAT" = on the hour nine-to-five weekdays
    //* "0 0 0 25 12 ?" = every Christmas Day at midnight
    //@Scheduled(cron = "*/60 * 9-17 * * MON-FRI")
    //@Scheduled(fixedRate = 60000)
    @Scheduled(cron = "*/60 * 9-13 * * MON-FRI")
    public void reportCurrency() {
        try {
            List<MaeTotalData> result = currencyRepository.findMaeCrawler();
            String sellRate = "0";
            for (Mae tds : result.get(0).getExchange()) {
                if (tds.getWheel().contains("CAM1") && tds.getAppliance().contains("UST / ART 000")) {
                   sellRate = tds.getSellRate().toString();
                }
            }
            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=%s";
            String apiToken4Gruop = "814592888:AAED7aTvtOGpuE1WiWXtfL8KjQrxv2ggfwo";
            String apiToken = "825328580:AAG1acLV0No7awRgIurNwjWDcdq0WrJjjwg";
            String chatId4Gruop = "-271523446";
            String chatId = "591887299";
            String text =   "%0A" +
                            "%0A\uD83D\uDCB0Mayorista: *$" + sellRate + "*" + //result.get(0).getSellRate() + "*" +
                            "%0A\uD83D\uDCC9 $(0.25)" +
                            "%0A\uD83D\uDCC8 $0.15" +
                            "%0A" +
                            "%0A\uD83C\uDF10 [FinancialApp](http://financialfront.herokuapp.com/currencies/)" +
                            "%0A [%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B](" + result.get(0).getEvolucionForexChartPaint() + ")";
            String parseMode = "MARKDOWN";

            urlString = String.format(urlString, apiToken, chatId, text, parseMode);

            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String response = sb.toString();
            JSONObject jsonBI = new JSONObject(response);
            if(!jsonBI.getBoolean("ok")){
                log.info(jsonBI.toString());
            }
        }catch (Exception e){
            log.info(e.toString());
        }
    }

    @Scheduled(cron = "* * 18 * * MON-FRI")
    public Double caprtureLastMaePrice() {
        try {
            return currencyService.findLastMaePrice();
        }catch (Exception e){
            return 0.0;
        }
    }
}
