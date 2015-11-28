package com.home.cc.currency.model;

import java.util.regex.Pattern;

/**
 * Created by prave_000 on 27/11/2015.
 */
public class CurrencyPattern {

    /**
     * regex used to match if the
     * currency pattern is valid
     */
    private String currencyMatcher;

    /**
     * regex used to extract the amount
     * without the symbols from the
     * incoming string
     */
    private String amountMatcher;

    /**
     * A padding string that will be
     * appended to the end of the incoming
     * amount if needed
     */
    private String paddingString;

    /**
     * Precompiled pattern instance for
     * currency
     */
    private Pattern currencyPattern;

    /**
     * precompiled pattern instance for amount
     */
    private Pattern amountPattern;

    /**
     * Constructor to use if padding is to be set
     *
     * @param currencyMatcher
     * @param amountMatcher
     * @param paddingString
     */
    public CurrencyPattern(String currencyMatcher, String amountMatcher, String paddingString) {
        this.currencyMatcher = currencyMatcher;
        this.amountMatcher = amountMatcher;
        this.paddingString = paddingString;
        setCurrencyPattern(Pattern.compile(getCurrencyMatcher()));
        setAmountPattern(Pattern.compile(getAmountMatcher()));
    }

    /**
     * Constructor
     *
     * @param currencyMatcher
     * @param amountMatcher
     */
    public CurrencyPattern(String currencyMatcher, String amountMatcher) {
        this.currencyMatcher = currencyMatcher;
        this.amountMatcher = amountMatcher;
        setCurrencyPattern(Pattern.compile(getCurrencyMatcher()));
        setAmountPattern(Pattern.compile(getAmountMatcher()));
    }


    public String getCurrencyMatcher() {
        return currencyMatcher;
    }

    public void setCurrencyMatcher(String currencyMatcher) {
        this.currencyMatcher = currencyMatcher;
    }

    public String getAmountMatcher() {
        return amountMatcher;
    }

    public void setAmountMatcher(String amountMatcher) {
        this.amountMatcher = amountMatcher;
    }

    public Pattern getCurrencyPattern() {
        return currencyPattern;
    }

    public void setCurrencyPattern(Pattern currencyPattern) {
        this.currencyPattern = currencyPattern;
    }

    public Pattern getAmountPattern() {
        return amountPattern;
    }

    public void setAmountPattern(Pattern amountPattern) {
        this.amountPattern = amountPattern;
    }

    public String getPaddingString() {
        return paddingString;
    }

    public void setPaddingString(String paddingString) {
        this.paddingString = paddingString;
    }
}
