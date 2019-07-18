package com.exchangerate.services;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.enums.Currency;

public interface RateService {

	RateDTO getRate(String date, Currency baseCurrency, Currency targetCurrency);
}
