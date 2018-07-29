package com.exchangerate.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exchangerate.domain.Rate;
import com.exchangerate.dto.RateDTO;
import com.exchangerate.exceptions.InvalidParameterException;
import com.exchangerate.repositories.RateRepository;

@Service
public class RateServiceImpl implements RateService {

	Calendar calendar = Calendar.getInstance();
	RestTemplate restTemplate = new RestTemplate();
	RateDTO rateDTO = new RateDTO();
	
	private final RateRepository rateRepository;

	public RateServiceImpl(RateRepository rateRepository) {
		this.rateRepository = rateRepository;
	}

	@Override
	public RateDTO getRate(String date, String baseCurrency, String targetCurrency) {

		calendar.setTime(getSimpleDateFormat(date));

		if (isValid(date)) {

			// get exchangeRate for that date and set it on the DTO
			Rate todayRate = restTemplate
					.getForObject("https://exchangeratesapi.io/api/" + date + "?base=" + baseCurrency, Rate.class);
			rateDTO.setExchangeRate(todayRate.getRates().get(targetCurrency));

			Rate rateDay1 = callApi(baseCurrency);
			Rate rateDay2 = callApi(baseCurrency);
			Rate rateDay3 = callApi(baseCurrency);
			Rate rateDay4 = callApi(baseCurrency);
			Rate rateDay5 = callApi(baseCurrency);
			
			// calculate averageFiveDays of 5 days and set it on the DTO
			rateDTO.setAverageFiveDays(
					calculateAverageFiveDays(targetCurrency, rateDay1, rateDay2, rateDay3, rateDay4, rateDay5));

			// calculate exchange rate trend, exchangeRateTrend, and set it on the DTO
			rateDTO.setExchangeRateTrend(
					calculateExchangeRateTrend(targetCurrency, rateDay1, rateDay2, rateDay3, rateDay4, rateDay5));

			// All successful queries should be persisted in the DB.
			rateRepository.save(rateDTO);
			
			// return DTO
			return rateDTO;

		}
	//	return null; // RETURN ERROR
		throw new InvalidParameterException("Couldn't parse Rate");
	}

	private boolean isValid(String text) {
		
		// check if fields are correct (YYYY-MM-DD)
		if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
			//return false;
			throw new InvalidParameterException("Date not correctly parsed");
		Date df = getSimpleDateFormat(text);
		if (df == null) {
		//	return false;
			throw new InvalidParameterException("Date not correctly parsed");
		}
		
		calendar.setTime(df);
		
		// Only dates between 2000-01-01 and yesterday are allowed.
		Calendar calendarYesterday =  Calendar.getInstance();
		calendarYesterday.add(Calendar.DATE, -1);
		
		 if((((calendar.get(Calendar.YEAR)) <=1999)) || (calendar.compareTo(calendarYesterday)>0)) {
		//	return false;
			throw new InvalidParameterException("Only dates between 2000-01-01 and yesterday are allowed.");
		}
		return true;
	}

	private Date getSimpleDateFormat(String text) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false);
		try {
			df.parse(text);
			return df.parse(text);
		} catch (ParseException ex) {
		//	return null;
			throw new InvalidParameterException("Date not correctly parsed");
		}
	}

	private Rate callApi(String baseCurrency) {

		calendar.add(Calendar.DATE, -1);
		
		// check if saturday or sunday
		while (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
			calendar.add(Calendar.DATE, -1);
		}

		String dateString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);
		Rate rate = restTemplate.getForObject("https://exchangeratesapi.io/api/" + dateString + "?base=" + baseCurrency,
				Rate.class);

		return rate;
	}

	private String calculateExchangeRateTrend(String targetCurrency, Rate rateDay1, Rate rateDay2, Rate rateDay3,
			Rate rateDay4, Rate rateDay5) {

		for (int i = -1; i <= 1; i++) {
			if ((rateDay1.getRates().get(targetCurrency).compareTo(rateDay2.getRates().get(targetCurrency)) == i)
					&& (rateDay2.getRates().get(targetCurrency).compareTo(rateDay3.getRates().get(targetCurrency)) == i)
					&& (rateDay3.getRates().get(targetCurrency).compareTo(rateDay4.getRates().get(targetCurrency)) == i)
					&& (rateDay4.getRates().get(targetCurrency).compareTo(rateDay5.getRates().get(targetCurrency)) == i)) {
				if (i == -1) {
					return "ascending";
				} else if (i == 0) {
					return "constant";
				} else if (i == 1) {
					return "descending";
				}
			}
		}
		return "undefined";
	}
	
	private BigDecimal calculateAverageFiveDays(String targetCurrency, Rate rateDay1, Rate rateDay2, Rate rateDay3,
			Rate rateDay4, Rate rateDay5) {
		
		BigDecimal averageFiveDays = (rateDay1.getRates().get(targetCurrency))
				.add(rateDay2.getRates().get(targetCurrency)).add(rateDay3.getRates().get(targetCurrency))
				.add(rateDay4.getRates().get(targetCurrency)).add(rateDay5.getRates().get(targetCurrency));

		averageFiveDays = averageFiveDays.divide(BigDecimal.valueOf(5));
		
		return averageFiveDays;
	}
}
