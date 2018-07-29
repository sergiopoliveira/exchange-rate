package com.exchangerate.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.repositories.RateRepository;

@Service
public class HistoricalInformationServiceImpl implements HistoricalInformationService{

	private final RateRepository rateRepository;
	
	public HistoricalInformationServiceImpl(RateRepository rateRepository) {
		this.rateRepository = rateRepository;
	}

	@Override
	public List<RateDTO> getDaily(int year, int month, int day) {
		
		// check if values are ok
		
		return rateRepository
				.findAll()
				.stream()
				.filter(rate -> rate.getYear() == year)
				.filter(rate -> rate.getMonth() == month)
				.filter(rate -> rate.getDay() == day)
				.map(rate -> {
					RateDTO rateDTO = rate;
					return rateDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<RateDTO> getMonthly(int year, int month) {

		return rateRepository
				.findAll()
				.stream()
				.filter(rate -> rate.getYear() == year)
				.filter(rate -> rate.getMonth() == month)
				.map(rate -> {
					RateDTO rateDTO = rate;
					return rateDTO;
				})
				.collect(Collectors.toList());
	}


}
