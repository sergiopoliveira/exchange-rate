package com.exchangerate.domain;

import com.exchangerate.enums.Currency;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RateTest {

    private Rate rate;

    @Before
    public void setUp() {
        rate = new Rate();
    }

    @Test
    public void testGetId() {
        Long idValue = 4L;
        rate.setId(idValue);
        assertEquals(idValue, rate.getId());
    }

    @Test
    public void testGetCurrency() {
        Currency currency = Currency.USD;
        rate.setBaseCurrency(currency);
        assertEquals(currency, rate.getBaseCurrency());
    }

}
