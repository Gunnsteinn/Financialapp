package com.financialapp.service;

import com.financialapp.model.Currency;
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


@Component
public class SchedulingTasksServices {

    @Autowired
    CurrencyService currencyService;


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
    @Scheduled(fixedRate = 60000)
    public void reportCurrency() {
        try {


            List<Currency> result = currencyService.findParticularCurrency("FM");
            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=%s";

            String apiToken4Gruop = "814592888:AAED7aTvtOGpuE1WiWXtfL8KjQrxv2ggfwo";
            String apiToken = "825328580:AAG1acLV0No7awRgIurNwjWDcdq0WrJjjwg";
            String chatId4Gruop = "-271523446";
            String chatId = "591887299";
            String text = "%0A" +
                          "%0A \uD83D\uDCB0Mayorista: <strong>$" +  result.get(0).getSellRate() + "</strong>" +
                          "%0A\uD83D\uDCC9 $(0.25)" +
                          "%0A\uD83D\uDCC8 $0.15" +
                          "%0A\uD83C\uDF10 <a href=\"http://financialfront.herokuapp.com/currencies\">FinancialApp</a>" +
                          "%0A";
            //https://api.telegram.org/bot825328580:AAG1acLV0No7awRgIurNwjWDcdq0WrJjjwg/sendMessage?chat_id=591887299&text=[%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B%E2%80%8B](http://png.pngtree.com/png_detail/20181019/simple-abstract-border-png-clipart_2592949.png)%20Some%20text%20here&parse_mode=MARKDOWN
            //curl -s -X POST "https://api.telegram.org/bot825328580:AAG1acLV0No7awRgIurNwjWDcdq0WrJjjwg/sendPhoto" -F chat_id=591887299 -F photo="http://www.mae.com.ar/temp/dcp_70943fc4-0-C3.png?guid=b311a5f3-c178-475c-84f8-a60c4779eb68" -F text="hola"

            String parseMode = "HTML";
            //URLEncoder.encode(q, "UTF-8");
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
        }catch (Exception e){}
    }
}
