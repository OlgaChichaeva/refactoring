package edu.refactor.demo.services;

import edu.refactor.demo.persist.entities.Money;
import edu.refactor.demo.projecttypes.Currency;
import org.springframework.stereotype.Service;

@Service(CashService.NAME)
public class CashServiceBean implements CashService {



    @Override
    public Money add(Money oneTerm, Money secondTerm) {
        return null;
    }

    @Override
    public Money subtract(Money oneTerm, Money secondTerm) {
        return null;
    }

    @Override
    public Money convert(Money cash, Currency toCurrency) {
        return null;
    }
}
