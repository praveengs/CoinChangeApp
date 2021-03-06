package com.home.cc;

import com.google.gson.Gson;
import com.home.cc.currency.CurrencyBuilder;
import com.home.cc.exception.InvalidInputException;
import com.home.cc.spring.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The entry end point class for finding change for the amount
 * <p>
 * Created by prave_000 on 25/11/2015.
 */
public class CoinChangeGenerator {
    /*
    The spring application context
     */
    private ApplicationContext context = null;

    /*
    The currency builder instance
     */
    private CurrencyBuilder currencyBuilder;

    /**
     * The default constructor
     */
    public CoinChangeGenerator() {
        setContext(new AnnotationConfigApplicationContext(AppConfig.class));
        setCurrencyBuilder(getContext().getBean(CurrencyBuilder.class));
    }

    /**
     * This method returns the change in a human friendly string format
     *
     * @param currency the currency name
     * @param amount   the amount
     * @return the minimum change in a string
     * @throws InvalidInputException
     */
    public String findMinimumChangeString(String currency, String amount) throws InvalidInputException {
        HashMap<String, String> coinChangeMap = getCurrencyBuilder().computeAmountDenoms(currency, amount);
        StringBuffer returnBuffer = new StringBuffer("");
        coinChangeMap.forEach((denomination, count) ->
                returnBuffer.append(count).append(" x ").append(denomination).append(", "));
        if (returnBuffer.length() > 0) {
            return returnBuffer.substring(0, returnBuffer.length() - 2);
        } else {
            throw new InvalidInputException("No change could be calculated for " + amount + " with currency " + currency);
        }
    }

    /**
     * This method returns the change in a machine and human friendly json format
     *
     * @param currency the currency
     * @param amount   the amount
     * @return the minimum change as a json
     * @throws InvalidInputException
     */
    public String findMinimumChangeJSON(String currency, String amount) throws InvalidInputException {

        HashMap<String, String> coinChangeMap = getCurrencyBuilder().computeAmountDenoms(currency, amount);

        Gson gson = new Gson();
        String coinChangeInJSON = gson.toJson(coinChangeMap);

        return coinChangeInJSON;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public CurrencyBuilder getCurrencyBuilder() {
        return currencyBuilder;
    }

    public void setCurrencyBuilder(CurrencyBuilder currencyBuilder) {
        this.currencyBuilder = currencyBuilder;
    }

    /**
     * This method is used to return all the available currencies
     *
     * @return list of currency names available
     */
    public List<String> retrieveAllCurrencieNames() {
        List<String> currenciesAvailable = new ArrayList<>(2);
        getCurrencyBuilder()
                .getCurrencyModels()
                .forEach(currencyModel ->
                        currenciesAvailable.add(currencyModel.getCurrencyName()));
        return currenciesAvailable;
    }
}
