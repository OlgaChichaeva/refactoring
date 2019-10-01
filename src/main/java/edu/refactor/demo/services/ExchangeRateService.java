package edu.refactor.demo.services;

import edu.refactor.demo.projecttypes.Currency;

import java.math.BigDecimal;

public interface ExchangeRateService {

    String NAME = "refactoring_ExchangeRateService";

    BigDecimal getFactor(Currency from, Currency to);
}
