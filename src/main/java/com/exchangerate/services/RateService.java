package com.exchangerate.services;

import com.exchangerate.dto.RateDTO;

public interface RateService {

	RateDTO getRate(String date, String baseCurrency, String targetCurrency);
}
