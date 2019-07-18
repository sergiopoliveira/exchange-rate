package com.exchangerate.domain;

import com.exchangerate.enums.Currency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String base;
    private String date;
    private HashMap<Currency, BigDecimal> rates;

    public Rate() {
    }

    public Rate(Long id, String base, String date, HashMap<Currency, BigDecimal> rates) {
        super();
        this.id = id;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<Currency, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(HashMap<Currency, BigDecimal> rates) {
        this.rates = rates;
    }

}
