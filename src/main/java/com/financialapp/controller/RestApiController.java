package com.financialapp.controller;

import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import com.financialapp.repository.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    CurrencyService currencyService; //Service which will do all data retrieval/manipulation work

    // -------------------Retrieve a Currency---------------------------------------------
    @RequestMapping(value = "/currency", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<List<Bank>> findAllCurrency() {
        List<Bank> allCurrencies = currencyService.findAllCurrency();
        if (allCurrencies.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Bank>>(allCurrencies, HttpStatus.OK);
    }

    // -------------------Retrieve all Currencies---------------------------------------------
    @RequestMapping(value = "/currency/{bank}", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<List<Currency>> findParticularCurrency(@PathVariable("bank") String bank) {
        List<Currency> currencies = currencyService.findParticularCurrency(bank);
        if (currencies.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Currency>>(currencies, HttpStatus.OK);
    }
}
