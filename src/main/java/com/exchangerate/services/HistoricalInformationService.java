package com.exchangerate.services;

import java.util.List;

import com.exchangerate.dto.RateDTO;

public interface HistoricalInformationService {

	List<RateDTO> getDaily(int year, int month, int day);
	
	List<RateDTO> getMonthly(int year, int month);
}
