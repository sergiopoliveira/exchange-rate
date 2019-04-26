package com.exchangerate.controllers;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.services.RateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RateController.BASE_URL)
public class RateController {

    public static final String BASE_URL = "/api/exchange-rate";
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @ApiOperation(value = "This will get the rate")
    @GetMapping("/{date}/{baseCurrency}/{targetCurrency}")
    @ResponseStatus(HttpStatus.OK)
    public RateDTO getRate(@PathVariable String date, @PathVariable String baseCurrency, @PathVariable String targetCurrency) {

        return rateService.getRate(date, baseCurrency, targetCurrency);
    }
}
