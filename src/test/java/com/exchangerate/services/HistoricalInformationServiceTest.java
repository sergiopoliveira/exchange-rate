package com.exchangerate.services;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.exceptions.InvalidParameterException;
import com.exchangerate.repositories.RateRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class HistoricalInformationServiceTest {


    private HistoricalInformationService historicalInformationService;

    @Mock
    private RateRepository rateRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        historicalInformationService = new HistoricalInformationServiceImpl(rateRepository);
    }

    @Test
    public void testMonthlyRates() {

        RateDTO rate1 = new RateDTO();
        RateDTO rate2 = new RateDTO();
        RateDTO rate3 = new RateDTO();

        rate1.setYear(2015);
        rate1.setMonth(5);

        rate2.setYear(2015);
        rate2.setMonth(5);

        rate3.setYear(2015);
        rate3.setMonth(5);

        // given
        List<RateDTO> rates = Arrays.asList(rate1, rate2, rate3);

        when(rateRepository.findAll()).thenReturn(rates);

        // when
        List<RateDTO> rateDTOs = historicalInformationService.getMonthly("2015", "05");

        // then
        assertEquals(3, rateDTOs.size());
    }

    @Test
    public void testDailyRates() {

        RateDTO rate1 = new RateDTO();
        RateDTO rate2 = new RateDTO();
        RateDTO rate3 = new RateDTO();

        rate1.setYear(2015);
        rate1.setMonth(5);
        rate1.setDay(1);

        rate2.setYear(2015);
        rate2.setMonth(5);
        rate2.setDay(1);

        rate3.setYear(2015);
        rate3.setMonth(5);
        rate3.setDay(1);

        // given
        List<RateDTO> rates = Arrays.asList(rate1, rate2, rate3);

        when(rateRepository.findAll()).thenReturn(rates);

        // when
        List<RateDTO> rateDTOs = historicalInformationService.getDaily("2015", "05", "01");

        // then
        assertEquals(3, rateDTOs.size());
    }

    @Test(expected = InvalidParameterException.class)
    public void testIncorrectlyParsedDateForDailyRate() {

        RateDTO rate1 = new RateDTO();
        rate1.setYear(2015);
        rate1.setMonth(14); // impossible month
        rate1.setDay(1);

        // given
        List<RateDTO> rates = Collections.singletonList(rate1);

        when(rateRepository.findAll()).thenReturn(rates);

        // when
        List<RateDTO> rateDTOs = historicalInformationService.getDaily("2015", "14", "01");

        // exception is thrown
    }

    @Test(expected = InvalidParameterException.class)
    public void testIncorrectlyParsedDateForMontlyRate() {

        RateDTO rate1 = new RateDTO();
        rate1.setYear(1990); // impossible year
        rate1.setMonth(10);
        rate1.setDay(1);

        // given
        List<RateDTO> rates = Collections.singletonList(rate1);

        when(rateRepository.findAll()).thenReturn(rates);

        // when
        List<RateDTO> rateDTOs = historicalInformationService.getMonthly("1990", "10");

        // exception is thrown
    }


}
