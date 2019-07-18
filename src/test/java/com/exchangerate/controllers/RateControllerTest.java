package com.exchangerate.controllers;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.enums.Currency;
import com.exchangerate.enums.ExchangeRateTrend;
import com.exchangerate.services.RateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RateControllerTest {

    @Mock
    private RateService rateService;

    @InjectMocks
    private RateController rateController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(rateController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testAscendingRate() throws Exception {

        RateDTO rate1 = new RateDTO();
        rate1.setId(1L);
        rate1.setExchangeRate(BigDecimal.valueOf(0.7));
        rate1.setAverageFiveDays(BigDecimal.valueOf(0.69));
        rate1.setExchangeRateTrend(ExchangeRateTrend.DESC);

        when(rateService.getRate("2010-03-19", Currency.EUR, Currency.CHF)).thenReturn(rate1);

        mockMvc.perform(get(RateController.BASE_URL + "/2010-03-19/EUR/CHF")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exchange_rate", equalTo(0.7)))
                .andExpect(jsonPath("$.average_five_days", equalTo(0.69)))
                .andExpect(jsonPath("$.exchange_rate_trend", equalTo(ExchangeRateTrend.DESC.toString())));
    }

    @Test
    public void testErrorURLFormat() throws Exception {

        //malformed URL
        mockMvc.perform(get(RateController.BASE_URL + "/foo/CHF")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}