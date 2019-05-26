package com.financialapp.util;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils{

    private static List<String> typeOfCurrencyList = Arrays.asList("DOLAR", "EURO", "REAL", "URUGUAYOS");
    private static Map<String, String> mapOCurrencyfCodes  = new HashMap<String, String>() {{
        put("DOLAR", "USD");
        put("EURO", "EUR");
        put("REAL", "BRL");
        put("URUGUAYOS", "UYU");
    }};

    public static String stringTypeNormalize(String s) {
        for(String temp : typeOfCurrencyList){
            Pattern pat = Pattern.compile(temp);
            Matcher mat = pat.matcher(Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase());

            if (mat.find()){
               s = mat.group();
            break;
           }
        }
        return s;
    }

    public static String stringCodeNormalize(String s) {
        return mapOCurrencyfCodes.get(stringTypeNormalize(s));
    }

    public static Double stringToDoubleNumber(String s) {
        Double num = 0.0;
        Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher matcher = regex.matcher(s.replace(',', '.'));
        while(matcher.find()){
            num = Double.valueOf(matcher.group(1));
        }
        return num;
    }

}




