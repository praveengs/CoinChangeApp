package com.home.cc.currency;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by prave_000 on 26/11/2015.
 */
public class CurrencyBuilder {

    private String currencyMatcher;
    private String amountMatcher;
    private String currencyDelimiter;
    private String paddingString;
    private Pattern pattern;
    private Pattern amountPattern;

    public CurrencyBuilder(String currencyMatcher, String amountMatcher, String currencyDelimiter) {
        setCurrencyMatcher(currencyMatcher);
        setAmountMatcher(amountMatcher);
        setCurrencyDelimiter(currencyDelimiter);
        setPattern(Pattern.compile(getCurrencyMatcher()));
        setAmountPattern(Pattern.compile(getAmountMatcher()));
    }

    public CurrencyBuilder(String currencyMatcher, String amountMatcher, String currencyDelimiter, String paddingString) {
        setCurrencyMatcher(currencyMatcher);
        setAmountMatcher(amountMatcher);
        setCurrencyDelimiter(currencyDelimiter);
        setPattern(Pattern.compile(getCurrencyMatcher()));
        setAmountPattern(Pattern.compile(getAmountMatcher()));
        setPaddingString(paddingString);
    }

    public boolean isAMatch(String currencyAmount) {
        boolean isAMatch = false;

        Matcher matcher = getPattern().matcher(currencyAmount);
        if (matcher.find()) {
            isAMatch = true;
        }
        return isAMatch;
    }

    public int getAmount(String currencyAmount) {
        Matcher matcher = getAmountPattern().matcher(currencyAmount);
        int amountToReturn = -1;
        if (matcher.find()) {
            String amount = matcher.group(0);

            if (getPaddingString() != null)
            {
                amount += getPaddingString();
            }
            String[] amountArray = amount.split(getCurrencyDelimiter());
            if (amountArray.length == 1) {
                //only lhs present
                amountToReturn = Integer.parseInt(amount);
            } else if (amountArray.length > 1) {
                amountToReturn = Integer.parseInt(amountArray[0]) * 100 +
                        Integer.parseInt(amountArray[1]);
            }
        }
        return amountToReturn;
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

    public String getCurrencyDelimiter() {
        return currencyDelimiter;
    }

    public void setCurrencyDelimiter(String currencyDelimiter) {
        this.currencyDelimiter = currencyDelimiter;
    }


    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
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
