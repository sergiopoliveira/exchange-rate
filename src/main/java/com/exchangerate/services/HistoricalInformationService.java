package com.exchangerate.services;

import java.util.List;

import com.exchangerate.dto.RateDTO;

public interface HistoricalInformationService {

	List<RateDTO> getDaily(String year, String month, String day);
	
	List<RateDTO> getMonthly(String year, String month);
}
