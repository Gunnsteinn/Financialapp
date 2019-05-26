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
@RequestMapping("/api")
@Api(value="Employee Management System", description="Operations pertaining to employee in Employee Management System")
public class RestApiController {

    @Autowired
    CurrencyService currencyService;

    @ApiOperation(value = "View a list of available employees", response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

    // -------------------Retrieve a Currency---------------------------------------------

    @RequestMapping(value = "/currency", method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<List<Bank>> findAllCurrency() throws ResourceNotFoundException {
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
