package com.exchangerate.services;

import com.exchangerate.dto.RateDTO;
import com.exchangerate.exceptions.InvalidParameterException;
import com.exchangerate.repositories.RateRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricalInformationServiceImpl implements HistoricalInformationService {

    private static final String DATE_NOT_CORRECTLY_PARSED = "Date not correctly parsed";
    private final RateRepository rateRepository;

    public HistoricalInformationServiceImpl(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public List<RateDTO> getDaily(String year, String month, String day) {

        // check if values are ok
        if (isValid(year, month, day)) {

            return rateRepository
                    .findAll()
                    .stream()
                    .filter(rate -> rate.getYear() == Integer.parseInt(year))
                    .filter(rate -> rate.getMonth() == Integer.parseInt(month))
                    .filter(rate -> rate.getDay() == Integer.parseInt(day))
                    .collect(Collectors.toList());
        }
        throw new InvalidParameterException(DATE_NOT_CORRECTLY_PARSED);
    }

    @Override
    public List<RateDTO> getMonthly(String year, String month) {

        if (isValid(year, month, "01")) {
            return rateRepository
                    .findAll()
                    .stream()
                    .filter(rate -> rate.getYear() == Integer.parseInt(year))
                    .filter(rate -> rate.getMonth() == Integer.parseInt(month))
                    .collect(Collectors.toList());
        }
        throw new InvalidParameterException(DATE_NOT_CORRECTLY_PARSED);
    }

    private boolean isValid(String year, String month, String day) {
        Calendar calendarYesterday = Calendar.getInstance();
        calendarYesterday.add(Calendar.DATE, -1);

        // check if fields are correct (YYYY-MM-DD)
        if ((!year.matches("^[0-9]{4}$") || (!month.matches("^[0-9]{2}$")) || (!day.matches("^[0-9]{2}$")))) {
            throw new InvalidParameterException(DATE_NOT_CORRECTLY_PARSED);
        }

        if (Integer.valueOf(year) < 2000 || Integer.valueOf(year) < 0) {
            throw new InvalidParameterException("Year not correctly parsed");
        }

        if (Integer.valueOf(month) > 12 || Integer.valueOf(month) < 0) {
            throw new InvalidParameterException("Month not correctly parsed");
        }
        if (Integer.valueOf(day) > 31 || Integer.valueOf(day) < 0) {
            throw new InvalidParameterException("Day not correctly parsed");
        }
        return true;
    }

}
