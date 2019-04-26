package com.exchangerate.controllers;

import com.exchangerate.dto.RateListDTO;
import com.exchangerate.services.HistoricalInformationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(HistoricalInformationController.BASE_URL)
public class HistoricalInformationController {

    public static final String BASE_URL = "/api/exchange-rate/history";
    private final HistoricalInformationService historicalInformationService;

    public HistoricalInformationController(HistoricalInformationService historicalInformationService) {
        this.historicalInformationService = historicalInformationService;
    }

    @ApiOperation(value = "The customer can get historical information using two API’s, one for the daily information")
    @GetMapping("/daily/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public RateListDTO getDaily(@PathVariable String year, @PathVariable String month, @PathVariable String day) {

        return new RateListDTO(historicalInformationService.getDaily(year, month, day));
    }

    @ApiOperation(value = "The customer can get historical information using two API’s, other for the monthly information.")
    @GetMapping("/monthly/{year}/{month}")
    @ResponseStatus(HttpStatus.OK)
    public RateListDTO getMonthly(@PathVariable String year, @PathVariable String month) {

        return new RateListDTO(historicalInformationService.getMonthly(year, month));
    }
}
