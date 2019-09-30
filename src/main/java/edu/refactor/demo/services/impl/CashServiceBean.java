package edu.refactor.demo.services.impl;

import edu.refactor.demo.entities.Money;
import edu.refactor.demo.projecttypes.Currency;
import edu.refactor.demo.services.CashService;
import edu.refactor.demo.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(CashService.NAME)
public class CashServiceBean implements CashService {

    @Autowired
    private ExchangeRateService exchangeRateService;

    /*
     * The final result is in currency of first term
     */
    @Override
    public Money add(Money oneTerm, Money secondTerm) {
        Money newSecondCash = convert(secondTerm, oneTerm.getCurrency());

        Money newCash = new Money();
        newCash.setCount(oneTerm.getCount().add(newSecondCash.getCount()));
        newCash.setCurrency(oneTerm.getCurrency());
        return newCash;
    }

    /*
     * The final result is in currency of first term
     */
    @Override
    public Money subtract(Money oneTerm, Money secondTerm) {
        Money newSecondCash = convert(secondTerm, oneTerm.getCurrency());

        Money newCash = new Money();
        newCash.setCount(oneTerm.getCount().subtract(newSecondCash.getCount()));
        newCash.setCurrency(oneTerm.getCurrency());
        return newCash;
    }

    @Override
    public Money convert(Money cash, Currency toCurrency) {
        BigDecimal oldCurrency = cash.getCount();
        BigDecimal factor = exchangeRateService.getFactor(cash.getCurrency(), toCurrency);
        BigDecimal newCurrency = oldCurrency.multiply(factor);

        Money newCash = new Money();
        newCash.setCount(newCurrency);
        newCash.setCurrency(toCurrency);
        return newCash;
    }
}
