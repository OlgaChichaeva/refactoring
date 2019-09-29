package edu.refactor.demo.services.cashandconvertation;

import edu.refactor.demo.projecttypes.Currency;

import java.math.BigDecimal;

public interface ExchangeRateService {

    String NAME = "refactoring_ExchangeRateService";

    public BigDecimal getFactor(Currency from, Currency to);
}
