package com.exchangerate.controllers;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.enums.Currency;
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

    @ApiOperation(value = "Retrieves the current rate, based on the base and target currency")
    @GetMapping("/{date}/{baseCurrency}/{targetCurrency}")
    @ResponseStatus(HttpStatus.OK)
    public RateDTO getRate(@PathVariable String date, @PathVariable Currency baseCurrency, @PathVariable Currency targetCurrency) {

        return rateService.getRate(date, baseCurrency, targetCurrency);
    }
}
