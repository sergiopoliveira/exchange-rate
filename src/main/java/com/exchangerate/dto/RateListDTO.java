package com.exchangerate.dto;

import java.util.List;

public class RateListDTO {

	private List<RateDTO> rates;

	public RateListDTO(List<RateDTO> rates) {
		this.rates = rates;
	}

	public RateListDTO() {
	}

	public List<RateDTO> getRates() {
		return rates;
	}

	public void setRates(List<RateDTO> rates) {
		this.rates = rates;
	}
	
	
}
