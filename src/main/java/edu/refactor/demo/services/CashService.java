package edu.refactor.demo.services;

import edu.refactor.demo.entities.Money;
import edu.refactor.demo.projecttypes.Currency;

public interface CashService {
    String NAME = "refactoring_CashService";

     Money add(Money oneTerm, Money secondTerm);

     Money subtract(Money oneTerm, Money secondTerm);

     Money convert(Money cash, Currency toCurrency);


}
