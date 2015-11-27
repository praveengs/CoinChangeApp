package com.home.cc;

import com.home.cc.currency.CurrencyBuilder;
import com.home.cc.exception.InvalidInputException;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Created by prave_000 on 25/11/2015.
 * <p>
 * Singleton class
 */
public class AmountToCanonicalEqGenerator {

    private static AmountToCanonicalEqGenerator instance = new AmountToCanonicalEqGenerator();
    private Collection<CurrencyBuilder> currencyBuilders;


    private AmountToCanonicalEqGenerator() {
        //to prevent initialisation

    }

    public static AmountToCanonicalEqGenerator getInstance() {
        return instance;
    }

    public int getCanonicalValue(String amount) throws InvalidInputException {
        int amountToReturn = -1;
        try {
            amountToReturn = (currencyBuilders.stream().
                    filter(currencyBuilder -> currencyBuilder.isAMatch(amount))
                    .findFirst().get()).getAmount(amount);
        } catch (NoSuchElementException e)
        {
            throw new InvalidInputException("The amount "+amount+" is invalid");
        }


        return amountToReturn;
    }

    public void setCurrencyBuilders(Collection<CurrencyBuilder> currencyBuilders) {
        this.currencyBuilders = currencyBuilders;
    }


}
