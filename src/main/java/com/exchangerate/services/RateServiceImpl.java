package com.exchangerate.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
		calendar.setTime(getSimpleDateFormat(date)); 
		
		//day 1...
		//remove one day
		String date_aux = "";
		calendar.add(Calendar.DATE, -1);
		date_aux = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
		Rate rateDay1 = restTemplate.getForObject("https://exchangeratesapi.io/api/" + date_aux + "?base=" + baseCurrency, Rate.class);
		
		// check if saturday or sunday

		//day 2...
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date_aux = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
		Rate rateDay2 = restTemplate.getForObject("https://exchangeratesapi.io/api/" + date_aux + "?base=" + baseCurrency, Rate.class);
	
		//day 3...
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date_aux = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
		Rate rateDay3 = restTemplate.getForObject("https://exchangeratesapi.io/api/" + date_aux + "?base=" + baseCurrency, Rate.class);
	
		//day 4...
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date_aux = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
		Rate rateDay4 = restTemplate.getForObject("https://exchangeratesapi.io/api/" + date_aux + "?base=" + baseCurrency, Rate.class);
	
		//day 5...
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date_aux = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
		Rate rateDay5 = restTemplate.getForObject("https://exchangeratesapi.io/api/" + date_aux + "?base=" + baseCurrency, Rate.class);
	
		
		// calculate averageFiveDays of 5 days and set it on the DTO 
		
		BigDecimal averageFiveDays = (rateDay1.getRates().get(targetCurrency))
								.add(rateDay2.getRates().get(targetCurrency)) 
								.add(rateDay3.getRates().get(targetCurrency))
								.add(rateDay4.getRates().get(targetCurrency)) 
								.add(rateDay5.getRates().get(targetCurrency)); 
		
		 averageFiveDays = averageFiveDays.divide(BigDecimal.valueOf(5)); 
		 rateDTO.setAverageFiveDays(averageFiveDays);
		 
		// calculate exchange rate trend, exchangeRateTrend, and set it on the DTO
		
		 // descending: when the exchange rates in the last five days are in strictly descending order,
		 if((rateDay1.getRates().get(targetCurrency).compareTo(rateDay2.getRates().get(targetCurrency)) == 1) && 
				 (rateDay2.getRates().get(targetCurrency).compareTo(rateDay3.getRates().get(targetCurrency)) == 1) &&
				 (rateDay3.getRates().get(targetCurrency).compareTo(rateDay4.getRates().get(targetCurrency)) == 1) &&
				 (rateDay4.getRates().get(targetCurrency).compareTo(rateDay5.getRates().get(targetCurrency)) == 1)) {
			 
			 rateDTO.setExchangeRateTrend("descending");
		 }
			
		 // ascending: when the exchange rates in the last five days are in strictly ascending order
		 else if((rateDay1.getRates().get(targetCurrency).compareTo(rateDay2.getRates().get(targetCurrency)) == -1) && 
				 (rateDay2.getRates().get(targetCurrency).compareTo(rateDay3.getRates().get(targetCurrency)) == -1) &&
				 (rateDay3.getRates().get(targetCurrency).compareTo(rateDay4.getRates().get(targetCurrency)) == -1) &&
				 (rateDay4.getRates().get(targetCurrency).compareTo(rateDay5.getRates().get(targetCurrency)) == -1)) {
			 
			 rateDTO.setExchangeRateTrend("ascending");
		 }
		 
		 // constant: when the exchange rates in the last five days are the same
		 else if((rateDay1.getRates().get(targetCurrency).compareTo(rateDay2.getRates().get(targetCurrency)) == 0) && 
				 (rateDay2.getRates().get(targetCurrency).compareTo(rateDay3.getRates().get(targetCurrency)) == 0) &&
				 (rateDay3.getRates().get(targetCurrency).compareTo(rateDay4.getRates().get(targetCurrency)) == 0) &&
				 (rateDay4.getRates().get(targetCurrency).compareTo(rateDay5.getRates().get(targetCurrency)) == 0)) {
			 
			 rateDTO.setExchangeRateTrend("constant");
		 }
		 else rateDTO.setExchangeRateTrend("undefined");
		 
				 
		// All successful queries should be persisted in the DB. 
		
		
		// return DTO
		return rateDTO;
		
		} 
		return null; //RETURN ERROR
	}
	
	
	private static boolean isValid(String text) {
	    if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
	        return false;
	    Date df =  getSimpleDateFormat(text);
	     if(df == null) {
	    	 return false;
	     }
	     return true;
	}


	private static Date getSimpleDateFormat(String text) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    df.setLenient(false);
	    try {
	        df.parse(text);
	        return  df.parse(text);
	    } catch (ParseException ex) {
	        return null;
	    }
	}

}
