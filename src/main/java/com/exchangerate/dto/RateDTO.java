package com.exchangerate.dto;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class RateDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("exchange_rate")
	private BigDecimal exchangeRate;

	@JsonProperty("average_five_days")
	private BigDecimal averageFiveDays;

	@JsonProperty("exchange_rate_trend")
	private String exchangeRateTrend;

	public RateDTO(Long id, BigDecimal exchangeRate, BigDecimal averageFiveDays, String exchangeRateTrend) {
		this.id = id;
		this.exchangeRate = exchangeRate;
		this.averageFiveDays = averageFiveDays;
		this.exchangeRateTrend = exchangeRateTrend;
	}

	public RateDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getAverageFiveDays() {
		return averageFiveDays;
	}

	public void setAverageFiveDays(BigDecimal averageFiveDays) {
		this.averageFiveDays = averageFiveDays;
	}

	public String getExchangeRateTrend() {
		return exchangeRateTrend;
	}

	public void setExchangeRateTrend(String exchangeRateTrend) {
		this.exchangeRateTrend = exchangeRateTrend;
	}

}
