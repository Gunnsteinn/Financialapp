package com.financialapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils{

//    @Value("#{'${type.currency.list}'.split(',')}")
//    private static List<String> typeOfCurrencyList;
//
//    private static List<String> list = Arrays.asList(new String[]{"DOLAR", "EURO", "REAL", "URUGUAYO"});
//
//    public static String stringNormalize(String s) {
//        for(String temp : list){
//            Pattern pat = Pattern.compile(temp);
//            Matcher mat = pat.matcher(Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase());
//
//            if (mat.find()){
//               s = mat.group();
//            break;
//           }
//        }
//        return s;
//    }

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




