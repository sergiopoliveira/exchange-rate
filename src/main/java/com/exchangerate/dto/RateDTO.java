package com.exchangerate.dto;

import com.exchangerate.enums.ExchangeRateTrend;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
public class RateDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty("exchange_rate")
    private BigDecimal exchangeRate;

    @JsonProperty("average_five_days")
    private BigDecimal averageFiveDays;

    @JsonProperty("exchange_rate_trend")
    private ExchangeRateTrend exchangeRateTrend;


    private Calendar calendar = Calendar.getInstance();

    @JsonIgnore
    private int year = calendar.get(Calendar.YEAR);
    @JsonIgnore
    private int month = calendar.get(Calendar.MONTH) + 1;
    @JsonIgnore
    private int day = calendar.get(Calendar.DAY_OF_MONTH);

    public RateDTO(Long id, BigDecimal exchangeRate, BigDecimal averageFiveDays, ExchangeRateTrend exchangeRateTrend) {
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

    public ExchangeRateTrend getExchangeRateTrend() {
        return exchangeRateTrend;
    }

    public void setExchangeRateTrend(ExchangeRateTrend exchangeRateTrend) {
        this.exchangeRateTrend = exchangeRateTrend;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }


}
