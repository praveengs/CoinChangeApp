package com.home.cc.currency.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The currency model class. Holds all the information about a
 * particular currency
 *
 * Created by prave_000 on 27/11/2015.
 */
public class CurrencyModel {

    /**
     * This denotes the sign used for
     * the major denomination
     * e.g. £
     */
    private String majorPartSymbol;

    /**
     * This denotes the sign used for
     * the minor denomination
     */
    private String minorPartSymbol;

    /**
     * The delimiter for this currency
     * e.g. "." , "-" etc
     */
    private String delimiter;

    /**
     * The patterns used in this particular currency
     */
    private Collection<CurrencyPattern> currencyPatterns;

    /**
     * The coins available for this currency
     * e.g. £1, £2, 1p, 2p etc
     */
    private Collection<String> coinsAvailable;

    /**
     * This will hold the coinsAvailable converted to
     * pence and stored in sorted order for faster
     * processing
     */
    private List<Integer> coinsInPenceSorted;

    /**
     * Constructor the the class.
     * Set through spring
     *
     * @param majorPartSymbol e.g £, Q etc
     * @param minorPartSymbol e.g. p
     * @param delimiter e.g .,- etc
     * @param currencyPatterns the currency matching patterns
     * @param coinsAvailable the available coins
     */
    public CurrencyModel(String majorPartSymbol, String minorPartSymbol, String delimiter, Collection<CurrencyPattern> currencyPatterns, Collection<String> coinsAvailable) {
        this.majorPartSymbol = majorPartSymbol;
        this.minorPartSymbol = minorPartSymbol;
        this.delimiter = delimiter;
        this.currencyPatterns = currencyPatterns;
        setCoinsAvailable(coinsAvailable);
    }


    public String getMajorPartSymbol() {
        return majorPartSymbol;
    }

    public void setMajorPartSymbol(String majorPartSymbol) {
        this.majorPartSymbol = majorPartSymbol;
    }

    public String getMinorPartSymbol() {
        return minorPartSymbol;
    }

    public void setMinorPartSymbol(String minorPartSymbol) {
        this.minorPartSymbol = minorPartSymbol;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Collection<CurrencyPattern> getCurrencyPatterns() {
        return currencyPatterns;
    }

    public void setCurrencyPatterns(Collection<CurrencyPattern> currencyPatterns) {
        this.currencyPatterns = currencyPatterns;
    }


    public Collection<String> getCoinsAvailable() {
        return coinsAvailable;
    }

    /**
     * Sets the available coins
     * Also invokes method to set the coins in actual denominations
     * and in sorted manner
     *
     * @param coinsAvailable coins available
     */
    public void setCoinsAvailable(Collection<String> coinsAvailable) {
        this.coinsAvailable = coinsAvailable;
        computeCoinsToPenceFormat();
    }

    /**
     * Method to convert the available coins to pence equivalant
     * denominations and sorting and storing them in the reverse
     * order
     */
    private void computeCoinsToPenceFormat() {
        setCoinsInPenceSorted(new ArrayList<>(getCoinsAvailable().size()));
        getCoinsAvailable().stream()
                .forEach(coin -> getCoinsInPenceSorted().add(convertToPence(coin)));

        Collections.sort(getCoinsInPenceSorted(), (c1, c2) -> c2 - c1);

    }

    /**
     * The logic for converting amount to pence
     * If it starts with the major part symbol of currency, multiply by 100.
     * Else if it ends with the minor part symbol of currency, store as it is
     *
     * @param coin
     * @return
     */
    private Integer convertToPence(String coin) {
        int retValue = 0;
        if (coin.startsWith(getMajorPartSymbol())) {
            retValue = Integer.parseInt(coin.substring(1)) * 100;
        } else if (coin.endsWith(getMinorPartSymbol())) {
            retValue = Integer.parseInt(coin.substring(0, coin.length() - 1));
        }
        return retValue;
    }

    public List<Integer> getCoinsInPenceSorted() {
        return coinsInPenceSorted;
    }

    public void setCoinsInPenceSorted(List<Integer> coinsInPenceSorted) {
        this.coinsInPenceSorted = coinsInPenceSorted;
    }
}
