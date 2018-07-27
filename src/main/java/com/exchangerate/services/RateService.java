package com.exchangerate.services;

import com.exchangerate.domain.RateDTO;

public interface RateService {

	RateDTO getRate(String date, String baseCurrency, String targetCurrency);
}
