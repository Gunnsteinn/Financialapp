package com.financialapp.controller;

import com.financialapp.exception.ResourceNotFoundException;
import com.financialapp.model.Bank;
import com.financialapp.model.Currency;
import com.financialapp.model.HomeBanking;
import com.financialapp.service.CurrencyService;
import com.financialapp.service.homeBanking.HomeBankingService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@Api(value = "Currency microservice", description = "This API has a CRUD for Currencies")
public class RestApiController {

    @Autowired
    CurrencyService currencyService;

    @Autowired
    HomeBankingService homeBankingService;

    // -------------------Retrieve all Currencies---------------------------------------------
    @RequestMapping(value = "/currency", method = RequestMethod.GET,produces = "application/json")
    @ApiOperation(value = "Find all bank Currency.", notes = "Return all bank Currency." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved lists of Banks."),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @CrossOrigin()
    public ResponseEntity<List<Bank>> findAllCurrency() throws ResourceNotFoundException {
        List<Bank> allCurrencies = currencyService.findAllCurrency();
        if (allCurrencies.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Bank>>(allCurrencies, HttpStatus.OK);
    }

    // -------------------Retrieve all open Currencies---------------------------------------------
    @RequestMapping(value = "/openCurrencies", method = RequestMethod.GET,produces = "application/json")
    @ApiOperation(value = "Find a bank Currency.", notes = "Return the last Mae Price." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully open Currencies."),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @CrossOrigin()
    public ResponseEntity<List<Bank>> findOpenCurrencies() {
        List<Bank> allCurrencies = currencyService.findOpenCurrencies();
        if (allCurrencies.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Bank>>(allCurrencies, HttpStatus.OK);
    }

    // -------------------Retrieve a Currency----------------------------------------------------
    @RequestMapping(value = "/currency/{bank}", method = RequestMethod.GET,produces = "application/json")
    @ApiOperation(value = "Find a bank Currency.", notes = "Return a bank Currency by Id." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved bank"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @CrossOrigin()
    public ResponseEntity<List<Currency>> findParticularCurrency(@PathVariable("bank") String bank) {
        List<Currency> currencies = currencyService.findParticularCurrency(bank);
        if (currencies.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Currency>>(currencies, HttpStatus.OK);
    }

    // -------------------Retrieve last Mae Price---------------------------------------------
    @RequestMapping(value = "/lastMaePrice", method = RequestMethod.GET,produces = "application/json")
    @ApiOperation(value = "Find a bank Currency.", notes = "Return the last Mae Price." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved last Mae Price."),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @CrossOrigin()
    public ResponseEntity<Double> findMaeTotalData() {
        Double lastMaePrice = currencyService.findLastMaePrice();
        return new ResponseEntity<Double>(lastMaePrice, HttpStatus.OK);
    }

    // -------------------Retrieve last Mae Price---------------------------------------------
    @RequestMapping(value = "/listHomeBanking", method = RequestMethod.GET,produces = "application/json")
    @ApiOperation(value = "Find a bank Currency.", notes = "Return the list of HomeBanking." )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of HomeBanking."),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
            @ApiResponse(code = 500, message = "Internal Server Error.")
    })
    @CrossOrigin()
    public ResponseEntity<List<HomeBanking>> getListHomeBanking() {
        List<HomeBanking> lastMaePrice = homeBankingService.getListHomeBanking();
        return new ResponseEntity<List<HomeBanking>>(lastMaePrice, HttpStatus.OK);
    }

}
