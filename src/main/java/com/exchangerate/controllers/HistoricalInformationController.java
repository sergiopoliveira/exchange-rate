package com.exchangerate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exchangerate.dto.RateListDTO;
import com.exchangerate.services.HistoricalInformationService;

import io.swagger.annotations.ApiOperation;

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
	public RateListDTO getDaily(@PathVariable int year, @PathVariable int month, @PathVariable int day) {

		return new RateListDTO(historicalInformationService.getDaily(year, month, day));
	}
	
	@ApiOperation(value = "The customer can get historical information using two API’s, other for the monthly information.")
	@GetMapping("/monthly/{year}/{month}")
	@ResponseStatus(HttpStatus.OK)
	public RateListDTO getMonthly(@PathVariable int year, @PathVariable int month) {

		return new RateListDTO(historicalInformationService.getMonthly(year, month));
	}
}
