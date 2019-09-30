package edu.refactor.demo.services.impl;

import edu.refactor.demo.projecttypes.Currency;
import edu.refactor.demo.services.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service(ExchangeRateService.NAME)
public class ExchangeRateServiceBean implements ExchangeRateService {

    private Map<Currency, BigDecimal> dollarRate;
    private Map<Currency, BigDecimal> euroRate;
    private Map<Currency, BigDecimal> rubleRate;

    public ExchangeRateServiceBean() {
        dollarRate = new HashMap<>();
        euroRate = new HashMap<>();
        rubleRate = new HashMap<>();

        dollarRate.put(Currency.EURO, BigDecimal.valueOf(0.91));
        dollarRate.put(Currency.RUBLE, BigDecimal.valueOf(64.66));

        euroRate.put(Currency.DOLLAR, BigDecimal.valueOf(1.09));
        euroRate.put(Currency.RUBLE, BigDecimal.valueOf(70.75));

        rubleRate.put(Currency.DOLLAR, BigDecimal.valueOf(0.015));
        rubleRate.put(Currency.EURO, BigDecimal.valueOf(0.014));
    }

    @Override
    public BigDecimal getFactor(Currency from, Currency to) {
        BigDecimal factor = null;

        if (from.equals(to)) {
            factor = BigDecimal.ONE;
        } else {
            switch (from) {
                case DOLLAR:
                    factor = dollarRate.get(to);
                    break;
                case EURO:
                    factor = euroRate.get(to);
                    break;
                case RUBLE:
                    factor = rubleRate.get(to);
                    break;
            }
        }
        return factor;
    }




}
