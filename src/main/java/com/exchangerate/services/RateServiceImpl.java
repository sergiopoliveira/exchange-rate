package com.exchangerate.services;

import com.exchangerate.domain.ExchangeRateTrend;
import com.exchangerate.domain.Rate;
import com.exchangerate.dto.RateDTO;
import com.exchangerate.exceptions.InvalidParameterException;
import com.exchangerate.repositories.RateRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RateServiceImpl implements RateService {

    private static final String DATE_NOT_CORRECTLY_PARSED = "Date not correctly parsed";
    private static final int FIVE_DAYS = 5;
    private Calendar calendar = Calendar.getInstance();
    private RestTemplate restTemplate = new RestTemplate();
    private RateDTO rateDTO = new RateDTO();

    private final RateRepository rateRepository;

    public RateServiceImpl(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public RateDTO getRate(String date, String baseCurrency, String targetCurrency) {

        calendar.setTime(getSimpleDateFormat(date));

        if (isValid(date)) {

            // get exchangeRate for that date and set it on the DTO
            Rate todayRate = restTemplate
                    .getForObject("https://api.exchangeratesapi.io/" + date + "?base=" + baseCurrency, Rate.class);
            rateDTO.setExchangeRate(todayRate.getRates().get(targetCurrency));

            List<Rate> rates = new ArrayList<>();

            for (int i = 0; i < FIVE_DAYS; i++) {
                rates.add(callApi(baseCurrency));
            }

            // calculate averageFiveDays of 5 days and set it on the DTO
            rateDTO.setAverageFiveDays(
                    calculateAverageRate(targetCurrency, rates));

            // calculate exchange rate trend, exchangeRateTrend, and set it on the DTO
            rateDTO.setExchangeRateTrend(
                    calculateExchangeRateTrend(targetCurrency, rates));

            // All successful queries should be persisted in the DB.
            rateDTO.setId(null);
            rateRepository.save(rateDTO);

            // return DTO
            return rateDTO;

        }
        throw new InvalidParameterException("Couldn't parse Rate");
    }

    private boolean isValid(String text) {

        // check if fields are correct (YYYY-MM-DD)
        if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
            throw new InvalidParameterException(DATE_NOT_CORRECTLY_PARSED);
        Date df = getSimpleDateFormat(text);
        if (df == null) {
            throw new InvalidParameterException(DATE_NOT_CORRECTLY_PARSED);
        }

        calendar.setTime(df);

        // Only dates between 2000-01-01 and yesterday are allowed.
        Calendar calendarYesterday = Calendar.getInstance();
        calendarYesterday.add(Calendar.DATE, -1);

        if (((calendar.get(Calendar.YEAR)) <= 1999) || (calendar.compareTo(calendarYesterday) > 0)) {
            throw new InvalidParameterException("Only dates between 2000-01-01 and yesterday are allowed.");
        }
        return true;
    }

    private Date getSimpleDateFormat(String text) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(text);
            return df.parse(text);
        } catch (ParseException ex) {
            throw new InvalidParameterException(DATE_NOT_CORRECTLY_PARSED);
        }
    }

    private Rate callApi(String baseCurrency) {

        calendar.add(Calendar.DATE, -1);

        // check if saturday or sunday
        while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        String dateString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH);

        return restTemplate.getForObject("https://api.exchangeratesapi.io/" + dateString + "?base=" + baseCurrency,
                Rate.class);
    }

    private ExchangeRateTrend calculateExchangeRateTrend(String targetCurrency, List<Rate> rates) {

        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j < rates.size(); j++) {
                if (rates.get(j).getRates().get(targetCurrency).compareTo(rates.get(j + 1).getRates().get(targetCurrency)) == i) {
                    continue;
                }
                if (i == -1) {
                    return ExchangeRateTrend.ASC;
                } else if (i == 0) {
                    return ExchangeRateTrend.CONS;
                } else {
                    return ExchangeRateTrend.DESC;
                }
            }
        }
        return ExchangeRateTrend.UNDEFINED;
    }

    private BigDecimal calculateAverageRate(String targetCurrency, List<Rate> rates) {

        BigDecimal sumRates = BigDecimal.ZERO;

        for (Rate rate : rates) {
            sumRates = sumRates.add(rate.getRates().get((targetCurrency)));
        }

        return sumRates.divide(BigDecimal.valueOf(5), RoundingMode.UP);
    }
}
