package com.financialapp.controller;

import com.financialapp.exception.ResourceNotFoundException;
import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import com.financialapp.service.CurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@Api(value = "Currency microservice", description = "This API has a CRUD for Currencies")
public class RestApiController {

    @Autowired
    CurrencyService currencyService;

    // -------------------Retrieve a Currency---------------------------------------------
    @RequestMapping(value = "/currency", method = RequestMethod.GET,produces = "application/json")
    @ApiOperation(value = "Find all bank Currency.", notes = "Return all bank Currency." )
    public ResponseEntity<List<Bank>> findAllCurrency() throws ResourceNotFoundException {
        List<Bank> allCurrencies = currencyService.findAllCurrency();
        if (allCurrencies.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Bank>>(allCurrencies, HttpStatus.OK);
    }

    // -------------------Retrieve all Currencies---------------------------------------------
    @RequestMapping(value = "/currency/{bank}", method = RequestMethod.GET,produces = "application/json")
    @ApiOperation(value = "Find a bank Currency.", notes = "Return a bank Currency by Id." )
    public ResponseEntity<List<Currency>> findParticularCurrency(@PathVariable("bank") String bank) {
        List<Currency> currencies = currencyService.findParticularCurrency(bank);
        if (currencies.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Currency>>(currencies, HttpStatus.OK);
    }
}
