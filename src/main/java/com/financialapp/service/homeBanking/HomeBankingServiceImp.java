package com.financialapp.service.homeBanking;

import com.financialapp.model.HomeBanking;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("homeBankingService")
public class HomeBankingServiceImp implements HomeBankingService {

    public List<HomeBanking> getListHomeBanking(){
        List<HomeBanking> listHomeBanking = new ArrayList<HomeBanking>();
        listHomeBanking.add(new HomeBanking("Banco de la Nación Argentina","https://hb.redlink.com.ar/bna/login.htm"));
        listHomeBanking.add(new HomeBanking("Banco Santander Rio","https://www2.personas.santanderrio.com.ar/obp-webapp/angular/?&_ga=2.245033029.1525919580.1560472119-208150692.1536442355#!/login"));
        listHomeBanking.add(new HomeBanking("Banco BBVA Francés","https://www.bbva.com.ar/"));
        listHomeBanking.add(new HomeBanking("Banco Galicia","https://onlinebanking.bancogalicia.com.ar/login?_ga=2.22504440.2122606651.1560473016-239445906.1556989425"));
        listHomeBanking.add(new HomeBanking("Banco Patagonia","https://ebankpersonas.bancopatagonia.com.ar/eBanking/usuarios/login.htm"));
        listHomeBanking.add(new HomeBanking("Banco ICBC","https://www.accessbanking.com.ar/RetailHomeBankingWeb/init.do?a=b&cm_mmc=icbc-_-access-_-header-_-onlinebanking"));
        listHomeBanking.add(new HomeBanking("Banco SuperVielle","https://personas.supervielle.com.ar/Login.aspx"));
        listHomeBanking.add(new HomeBanking("Banco Provincia de Buenos Aires","https://www.bancoprovincia.bancainternet.com.ar/eBanking/login/inicio.htm"));
        listHomeBanking.add(new HomeBanking("Banco Provincia de Córdoba","https://www.bancor.com.ar/bancon/"));
        return listHomeBanking;
    }
}