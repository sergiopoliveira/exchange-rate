package com.exchangerate.services;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.enums.Currency;
import com.exchangerate.exceptions.InvalidParameterException;
import com.exchangerate.repositories.RateRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class RateServiceTest {

    private RateService rateService;

    @Mock
    private RateRepository rateRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        rateService = new RateServiceImpl(rateRepository);
    }

    @Test
    public void testRate() {

        RateDTO rate1 = new RateDTO();
        Calendar calendar = Calendar.getInstance();

        rate1.setYear(calendar.get(Calendar.YEAR));
        rate1.setMonth(calendar.get(Calendar.MONTH) + 1);
        rate1.setDay(calendar.get(Calendar.DAY_OF_MONTH));

        // given
        when(rateService.getRate("2015-03-03", Currency.EUR, Currency.USD)).thenReturn(rate1);

        // when
        RateDTO rateDTO = rateService.getRate("2015-03-03", Currency.EUR, Currency.USD);

        // then
        assertEquals(rate1.getYear(), rateDTO.getYear());
        assertEquals(rate1.getMonth(), rateDTO.getMonth());
        assertEquals(rate1.getDay(), rateDTO.getDay());
    }

    @Test(expected = InvalidParameterException.class)
    public void testInvalidDate() {

        RateDTO rate1 = new RateDTO();
        Calendar calendar = Calendar.getInstance();

        rate1.setYear(calendar.get(Calendar.YEAR));
        rate1.setMonth(calendar.get(Calendar.MONTH) + 1);
        rate1.setDay(calendar.get(Calendar.DAY_OF_MONTH));

        // given
        when(rateService.getRate("1990-03-03", Currency.EUR, Currency.USD)).thenReturn(rate1);

        // when
        RateDTO rateDTO = rateService.getRate("1990-03-03", Currency.EUR, Currency.USD);

    }

}
