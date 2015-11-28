package com.home.cc.currency;

import com.home.cc.currency.model.CurrencyModel;
import com.home.cc.currency.model.CurrencyPattern;
import com.home.cc.exception.InvalidInputException;


import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by prave_000 on 26/11/2015.
 */
public class CurrencyBuilder {

    /**
     * Inner class used to pass around values
     */
    public class ValueExchangeModel {
        /*
        Amount in canonical form
         */
        private int amountInCanonicalForm;
        /*
        The currency model
         */
        private CurrencyModel currencyModel;

        /**
         * constructor
         *
         * @param amountInCanonicalForm
         * @param currencyModel
         */
        public ValueExchangeModel(int amountInCanonicalForm, CurrencyModel currencyModel) {
            this.amountInCanonicalForm = amountInCanonicalForm;
            this.currencyModel = currencyModel;
        }

        public int getAmountInCanonicalForm() {
            return amountInCanonicalForm;
        }

        public CurrencyModel getCurrencyModel() {
            return currencyModel;
        }

    }

    /*
    The currency models, set in by spring
     */
    private Collection<CurrencyModel> currencyModels;

    /*
    singleton instance for this class
     */
    private static CurrencyBuilder instance = new CurrencyBuilder();

    /**
     * default constructor to avid instantiation
     */
    private CurrencyBuilder() {
        //To avoid instantiation outside
    }

    /**
     * method to return the instance of this singleton
     *
     * @return
     */
    public static CurrencyBuilder getInstance() {
        return instance;
    }

    /**
     * this method computes the canonical value of the incoming
     * amount and returns it along with the currency model to
     * which it belongs to, for further computations
     *
     * @param amount
     * @return
     * @throws InvalidInputException
     */
    public ValueExchangeModel computeCanonicalValue(String amount) throws InvalidInputException {
        ValueExchangeModel valueExchangeModel = null;
        int amountToReturn = -1;

        for (CurrencyModel currencyModel : getCurrencyModels()) {
            try {
                amountToReturn = getAmount(amount, currencyModel.getCurrencyPatterns().parallelStream()
                        .filter(currencyPattern -> isAMatch(amount, currencyPattern.getCurrencyPattern()))
                        .findFirst().get(), currencyModel.getDelimiter());
                valueExchangeModel = new ValueExchangeModel(amountToReturn, currencyModel);
            } catch (NoSuchElementException e) {
                //Catching to handle separately
            }
        }
        if (amountToReturn == -1) {
            throw new InvalidInputException("The amount " + amount + " is invalid");
        }

        return valueExchangeModel;
    }

    /**
     * This method checks if the incoming currency in string format
     * matches the pattern.
     *
     * @param currencyAmount
     * @param currencyPattern
     * @return
     */
    public boolean isAMatch(String currencyAmount, Pattern currencyPattern) {
        boolean isAMatch = false;

        Matcher matcher = currencyPattern.matcher(currencyAmount);
        if (matcher.find()) {
            isAMatch = true;
        }
        return isAMatch;
    }

    /**
     * This method uses the currency pattern along with the delimiter
     * to parse the amount out of the incoming string, and then convert
     * the amount into smaller denomination for easier computation
     *
     * @param currencyAmount
     * @param currencyPattern
     * @param delimiter
     * @return
     */
    public int getAmount(String currencyAmount, CurrencyPattern currencyPattern, String delimiter) {
        Matcher matcher = currencyPattern.getAmountPattern().matcher(currencyAmount);
        int amountToReturn = -1;
        if (matcher.find()) {
            String amount = matcher.group(0);

            if (currencyPattern.getPaddingString() != null) {
                amount += currencyPattern.getPaddingString();
            }
            String[] amountArray = amount.split(delimiter);
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

    /**
     * This method is the entry point for the computation
     * It gets the canonical form and the currency model used for the computation
     * It then uses that to find the denominations and counts of it that can be used
     * to arrive at the amount.
     * It then uses those denominations to convert them into actual
     * currency model and get the count of each with the currency symbols
     *
     * @param amount
     * @return a hashmap with key as the denomination and value as count
     * @throws InvalidInputException
     */
    public HashMap<String, String> computeAmountDenoms(String amount) throws InvalidInputException {
        //Compute the canonical form of the incoming amount
        //and also get the currency model for the amount
        ValueExchangeModel valueExchangeModel = computeCanonicalValue(amount);

        CurrencyModel currencyModel = valueExchangeModel.getCurrencyModel();

        //Now compute the change and its counts
        HashMap<Integer, Integer> countMap =
                computeAmountDenoms(valueExchangeModel.getAmountInCanonicalForm(),
                        currencyModel.getCoinsInPenceSorted());

        //Convert the denominations and counts to currency symbol format
        HashMap<String, String> retMap =
                convertDenomCountMapToStringMap(countMap, currencyModel.getMajorPartSymbol(),
                        currencyModel.getMinorPartSymbol());
        return retMap;
    }

    /**
     * Entry point to computations using the amount in canonical form
     * and the collection of coins available to make the change
     * <p>
     * The collection of coins is assumed to be sorted in the reverse
     * order of denomination
     *
     * @param amountInPence
     * @param coinsInPenceSorted
     * @return a hashmap with denomination as key and count as value
     */
    private HashMap<Integer, Integer> computeAmountDenoms(int amountInPence, List<Integer> coinsInPenceSorted) {

        HashMap<Integer, Integer> denomCountMap = new LinkedHashMap<>(1);
        if (amountInPence > 0) {
            countForAmount(amountInPence, denomCountMap, coinsInPenceSorted, 0);
        }
        return denomCountMap;
    }

    /**
     * This method uses the integer entries in one hashmap to currency symbol equivalant
     * entries in the other
     *
     * @param denomCountMap
     * @param majorPartSymbol
     * @param minorPartSymbol
     * @return
     */
    private HashMap<String, String> convertDenomCountMapToStringMap(HashMap<Integer, Integer> denomCountMap,
                                                                    String majorPartSymbol, String minorPartSymbol) {
        HashMap<String, String> convertedMap = new LinkedHashMap<>(denomCountMap.size());
        //Iterate through each entry and convert them and store them into the new hash map
        denomCountMap.forEach(
                (denom, count) -> convertedMap.put(
                        convertPenceToCurr(denom, majorPartSymbol, minorPartSymbol), String.valueOf(count)));
        return convertedMap;
    }

    /**
     * This method checks if its a major currency equivalant
     * or a minor currency equivalant.
     *
     * The logic is to divide the denomination by 100, and if the quotient is greater
     * than 0 then its a major currency and attach the major symbol to the beginning.
     *
     * Else attach the minor symbol to the end
     *
     * @param denom
     * @param majorPartSymbol
     * @param minorPartSymbol
     * @return
     */
    private String convertPenceToCurr(Integer denom, String majorPartSymbol, String minorPartSymbol) {
        if (denom / 100 > 0) {
            return majorPartSymbol + (denom / 100);
        } else {
            return denom + minorPartSymbol;
        }
    }


    /**
     * This method computes recursively reducing the amount using the highest denomination available
     * until the amount becomes zero. A hashmap is used to store the data of he denominations used
     * so far and the count of times it has been used.
     *
     * @param amountInPence
     * @param denomCountMap
     * @param coinsInPenceSorted
     * @param index
     */
    private void countForAmount(int amountInPence, HashMap<Integer, Integer> denomCountMap,
                                List<Integer> coinsInPenceSorted, int index) {

        if (amountInPence == 0) {
            return;
        }
        int currentDenom = coinsInPenceSorted.get(index);
        int amount = amountInPence - currentDenom;
        if (amount >= 0) {
            Integer denomCount = denomCountMap.get(currentDenom);
            denomCountMap.put(currentDenom, (denomCount == null ? 1 : denomCount + 1));
            countForAmount(amount, denomCountMap, coinsInPenceSorted, index);
        } else {
            countForAmount(amountInPence, denomCountMap, coinsInPenceSorted, index + 1);
        }
    }

    /**
     * Gettter for the currency models
     *
     * @return
     */
    public Collection<CurrencyModel> getCurrencyModels() {
        return currencyModels;
    }

    /**
     * Setter for the currency models
     * @param currencyModels
     */
    public void setCurrencyModels(Collection<CurrencyModel> currencyModels) {
        this.currencyModels = currencyModels;
    }
}
