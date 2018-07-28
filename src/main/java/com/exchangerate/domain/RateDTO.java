package com.exchangerate.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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
}
