package edu.refactor.demo.services;

import edu.refactor.demo.entities.Money;
import edu.refactor.demo.projecttypes.Currency;

public interface CashService {
    String NAME = "refactoring_CashService";

    public Money add(Money oneTerm, Money secondTerm);

    public Money subtract(Money oneTerm, Money secondTerm);

    public Money convert(Money cash, Currency toCurrency);


}
