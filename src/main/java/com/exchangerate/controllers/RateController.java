package com.exchangerate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.services.RateService;

import io.swagger.annotations.ApiOperation;

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
