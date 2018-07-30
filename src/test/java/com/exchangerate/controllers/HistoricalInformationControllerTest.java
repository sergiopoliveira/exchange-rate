package com.exchangerate.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.services.HistoricalInformationService;

public class HistoricalInformationControllerTest {

	@Mock
	HistoricalInformationService historicalInformationService;
	
	@InjectMocks
	HistoricalInformationController historicalInformationController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(historicalInformationController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}
	
	@Test
	public void testListMonthlyHistoricalInformation() throws Exception {
		RateDTO rate1 = new RateDTO();
		rate1.setAverageFiveDays(BigDecimal.valueOf(0.5));
		rate1.setExchangeRate(BigDecimal.valueOf(0.9));
		rate1.setExchangeRateTrend("ascending");
		
		RateDTO rate2 = new RateDTO();
		rate2.setAverageFiveDays(BigDecimal.valueOf(0.4));
		rate2.setExchangeRate(BigDecimal.valueOf(0.4));
		rate2.setExchangeRateTrend("constant");
		
		List<RateDTO> rates = Arrays.asList(rate1, rate2);
		
		when(historicalInformationService.getMonthly("2015", "02")).thenReturn(rates);
		
		mockMvc.perform(get(HistoricalInformationController.BASE_URL + "/monthly/2015/02")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.rates", hasSize(2)));
	}
	
	@Test
	public void testListDailyHistoricalInformation() throws Exception {
		RateDTO rate1 = new RateDTO();
		rate1.setAverageFiveDays(BigDecimal.valueOf(0.5));
		rate1.setExchangeRate(BigDecimal.valueOf(0.9));
		rate1.setExchangeRateTrend("ascending");
		
		RateDTO rate2 = new RateDTO();
		rate2.setAverageFiveDays(BigDecimal.valueOf(0.4));
		rate2.setExchangeRate(BigDecimal.valueOf(0.4));
		rate2.setExchangeRateTrend("constant");
		
		List<RateDTO> rates = Arrays.asList(rate1, rate2);
		
		when(historicalInformationService.getDaily("2015", "02","03")).thenReturn(rates);
		
		mockMvc.perform(get(HistoricalInformationController.BASE_URL + "/daily/2015/02/03")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.rates", hasSize(2)));
	}
	
	@Test
	public void testErrorURLFormat() throws Exception {
		
		//malformed URL
		mockMvc.perform(get(HistoricalInformationController.BASE_URL + "/aa/CHF")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
}	
}
