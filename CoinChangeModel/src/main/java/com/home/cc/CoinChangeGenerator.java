package com.home.cc;

import com.google.gson.Gson;
import com.home.cc.currency.CurrencyBuilder;
import com.home.cc.exception.InvalidInputException;
import com.home.cc.spring.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;

/**
 * The entry end point class for finding change for the amount
 *
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
     * @param amount
     * @return
     * @throws InvalidInputException
     */
    public String findMinimumChangeString(String amount) throws InvalidInputException {
        HashMap<String, String> coinChangeMap = getCurrencyBuilder().computeAmountDenoms(amount);
        StringBuffer returnBuffer = new StringBuffer("");
        coinChangeMap.forEach((denomination, count) ->
                returnBuffer.append(count).append(" x ").append(denomination).append(", "));
        return returnBuffer.substring(0, returnBuffer.length()-2);
    }

    /**
     * This method returns the change in a machine and human friendly json format
     * @param amount
     * @return
     * @throws InvalidInputException
     */
    public String findMinimumChangeJSON(String amount) throws InvalidInputException {

        HashMap<String, String> coinChangeMap = getCurrencyBuilder().computeAmountDenoms(amount);

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
}
