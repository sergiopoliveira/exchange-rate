package com.exchangerate.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exchangerate.domain.Rate;
import com.exchangerate.domain.RateDTO;

@Service
public class RateServiceImpl implements RateService {

	@Override
	public RateDTO getRate(String date, String baseCurrency, String targetCurrency) {
		
		RestTemplate restTemplate = new RestTemplate();
		RateDTO rateDTO = new RateDTO();
		
		// check if fields are correct (YYYY-MM-DD)
		// Only dates between 2000-01-01 and yesterday are allowed.
		
		if(isValid(date)) {
			
		
		// get exchangeRate for that date and set it on the DTO 
		Rate todayRate = restTemplate.getForObject("https://exchangeratesapi.io/api/" + date + "?base=" + baseCurrency, Rate.class);
		rateDTO.setExchangeRate(todayRate.getRates().get(targetCurrency));
		
		// call API for last 5 days, excluding Saturday and Sunday
		Calendar calendar = Calendar.getInstance();
		calendar = getSimpleDateFormat(date).getCalendar();
		
		//day 1...
		//remove one day
		calendar.roll(Calendar.DATE, false);
		
		// check if saturday or sunday
		if(calendar.DAY_OF_WEEK == 1 || calendar.DAY_OF_WEEK == 7)

		//day 2...
		//day 3...
		//day 4...
		//day 5...
		
		// calculate averageFiveDays of 5 days and set it on the DTO 
		
		// calculate exchange rate trend, exchangeRateTrend, and set it on the DTO
		
		
		// All successful queries should be persisted in the DB. 
		
		
		// return DTO
		return rateDTO;
		
		} 
		return null; //RETURN ERROR
		
	}
	
	
	private static boolean isValid(String text) {
	    if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
	        return false;
	    SimpleDateFormat df =  getSimpleDateFormat(text);
	     if(df == null) {
	    	 return false;
	     }
	     return true;
	}


	private static SimpleDateFormat getSimpleDateFormat(String text) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    df.setLenient(false);
	    try {
	        df.parse(text);
	        return df;
	    } catch (ParseException ex) {
	        return null;
	    }
	}

}
