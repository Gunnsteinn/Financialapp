package com.financialapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class SchedulingTasksServices {

    @Autowired
    CurrencyService currencyService;

    private static final Logger log = LoggerFactory.getLogger(SchedulingTasksServices.class);

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        log.info(currencyService.resetAllEntries());
        currencyService.findAllCurrency();
    }
}
