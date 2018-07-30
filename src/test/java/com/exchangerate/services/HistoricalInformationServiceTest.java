package com.exchangerate.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.repositories.RateRepository;

public class HistoricalInformationServiceTest {

	
	HistoricalInformationService historicalInformationService;
	
	@Mock
	RateRepository rateRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		historicalInformationService = new HistoricalInformationServiceImpl(rateRepository);
}
	
	@Test
	public void getMonthlyRates() throws Exception {

		RateDTO rate1 = new RateDTO();
		RateDTO rate2 = new RateDTO();
		RateDTO rate3 = new RateDTO();
		
		rate1.setYear(2015);
		rate1.setMonth(05);
		
		rate2.setYear(2015);
		rate2.setMonth(05);
		
		rate3.setYear(2015);
		rate3.setMonth(05);
		
		// given
		List<RateDTO> rates = Arrays.asList(rate1,rate2,rate3);
		
		when(rateRepository.findAll()).thenReturn(rates);
		
		// when
		List<RateDTO> rateDTOs = historicalInformationService.getMonthly("2015", "05");
		
		// then
		assertEquals(3, rateDTOs.size());
}
	
	@Test
	public void getDailyyRates() throws Exception {

		RateDTO rate1 = new RateDTO();
		RateDTO rate2 = new RateDTO();
		RateDTO rate3 = new RateDTO();
		
		rate1.setYear(2015);
		rate1.setMonth(05);
		rate1.setDay(01);
		
		rate2.setYear(2015);
		rate2.setMonth(05);
		rate2.setDay(01);
		
		rate3.setYear(2015);
		rate3.setMonth(05);
		rate3.setDay(01);
		
		// given
		List<RateDTO> rates = Arrays.asList(rate1,rate2,rate3);
		
		when(rateRepository.findAll()).thenReturn(rates);
		
		// when
		List<RateDTO> rateDTOs = historicalInformationService.getDaily("2015", "05","01");
		
		// then
		assertEquals(3, rateDTOs.size());
}
}
