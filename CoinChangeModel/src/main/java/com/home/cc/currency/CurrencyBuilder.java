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
 * This is the class that helps
 * in building currency amounts
 * <p>
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
         * @param amountInCanonicalForm the value in canonical form
         * @param currencyModel         the currency model
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
     * @return the instance
     */
    public static CurrencyBuilder getInstance() {
        return instance;
    }

    /**
     * this method computes the canonical value of the incoming
     * amount and returns it along with the currency model to
     * which it belongs to, for further computations
     *
     * @param currency the currency to match
     * @param amount   the amount to convert
     * @return an internal class to pass around the value and the currency model
     * @throws InvalidInputException
     */
    public ValueExchangeModel computeCanonicalValue(String currency, String amount) throws InvalidInputException {
        ValueExchangeModel valueExchangeModel = null;
        int amountToReturn = -1;

        for (CurrencyModel currencyModel : getCurrencyModels()) {
            if (currencyModel.getCurrencyName().equalsIgnoreCase(currency)) {
                try {
                    //Loop through the patterns in currency model and find the first matching one
                    //Using the amount pattern within the currency pattern from the model
                    //find the amount. Set the amount and the currency details in the
                    //valueExchange model for further computations
                    amountToReturn = getAmount(amount
                            , currencyModel.getCurrencyPatterns()
                                    .parallelStream()
                                    .filter(currencyPattern -> isAMatch(amount, currencyPattern.getCurrencyPattern()))
                                    .findFirst().get()
                            , currencyModel.getDelimiter()
                            , currencyModel.getConversionDenomination());
                    valueExchangeModel = new ValueExchangeModel(amountToReturn, currencyModel);
                } catch (NoSuchElementException e) {
                    throw new InvalidInputException("The amount " + amount + " is invalid for the currency " + currency);
                }
            }
        }

        if (amountToReturn == -1) {
            throw new InvalidInputException("Either the amount " + amount + " or the currency "
                    + currency +" is invalid");
        }
        return valueExchangeModel;
    }

    /**
     * This method checks if the incoming currency in string format
     * matches the pattern.
     *
     * @param currencyAmount  the currency amount to match
     * @param currencyPattern the currency pattern to do the matching
     * @return true if matches
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
     * @param currencyAmount         the currency amount to convert
     * @param currencyPattern        the amount pattern to extract the amount
     * @param delimiter              the delimiter to separate the amounts
     * @param conversionDenomination the conversion denomination
     * @return returns the amount in canonical form
     */
    public int getAmount(String currencyAmount, CurrencyPattern currencyPattern,
                         String delimiter, int conversionDenomination) throws InvalidInputException {


        Matcher matcher = currencyPattern.getAmountPattern().matcher(currencyAmount);
        int amountToReturn = -1;
        if (matcher.find()) {
            String amount = matcher.group(0);

            if (currencyPattern.getPaddingString() != null) {
                amount += currencyPattern.getPaddingString();
            }

            //Stripping off all leading zeros
            amount = amount.replaceFirst("^0+(?!$)", "");

            //Splitting them on delimiter
            String[] amountArray = amount.split(delimiter);
            if (amountArray.length == 1) {
                if (amount.length() > 5) {
                    throw new InvalidInputException("Unable to handle the large amount, try a smaller one");
                }
                //only lhs present
                amountToReturn = Integer.parseInt(amount);
            } else if (amountArray.length > 1) {
                if (amountArray[0].length() + amountArray[1].length() > 5) {
                    throw new InvalidInputException("Unable to handle the large amount, try a smaller one");
                }
                int lhs = Integer.parseInt(amountArray[0]);
                int rhs = Integer.parseInt(amountArray[1]);

                if ((rhs / conversionDenomination) > 0)
                {
                    throw new InvalidInputException("Invalid denomination "+ rhs +" for the currency");
                }
                amountToReturn = (lhs * conversionDenomination) + rhs;
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
     * @param currency the currency to match
     * @param amount   the incoming amount
     * @return a hashmap with key as the denomination and value as count
     * @throws InvalidInputException
     */
    public HashMap<String, String> computeAmountDenoms(String currency, String amount) throws InvalidInputException {
        //Compute the canonical form of the incoming amount
        //and also get the currency model for the amount
        ValueExchangeModel valueExchangeModel = computeCanonicalValue(currency, amount);

        CurrencyModel currencyModel = valueExchangeModel.getCurrencyModel();

        //Now compute the change and its counts
        HashMap<Integer, Integer> countMap =
                computeAmountDenoms(valueExchangeModel.getAmountInCanonicalForm(),
                        currencyModel.getCoinsInPenceSorted());

        //Convert the denominations and counts to currency symbol format and
        //return the map
        return convertDenomCountMapToStringMap(countMap, currencyModel.getMajorPartSymbol(),
                currencyModel.getMinorPartSymbol(), currencyModel.getConversionDenomination());
    }

    /**
     * Entry point to computations using the amount in canonical form
     * and the collection of coins available to make the change
     * <p>
     * The collection of coins is assumed to be sorted in the reverse
     * order of denomination
     *
     * @param amountInPence      amount in pence
     * @param coinsInPenceSorted coins sorted in reverse order
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
     * @param denomCountMap          the incoming map
     * @param majorPartSymbol        the major part symbol
     * @param minorPartSymbol        the minor part symbol
     * @param conversionDenomination the conversion denomination
     * @return converting the incoming integer map to string map
     */
    private HashMap<String, String> convertDenomCountMapToStringMap(HashMap<Integer, Integer> denomCountMap,
                                                                    String majorPartSymbol, String minorPartSymbol,
                                                                    int conversionDenomination) {
        HashMap<String, String> convertedMap = new LinkedHashMap<>(denomCountMap.size());
        //Iterate through each entry and convert them and store them into the new hash map
        denomCountMap.forEach(
                (denom, count) -> convertedMap.put(
                        convertPenceToCurr(denom, majorPartSymbol, minorPartSymbol, conversionDenomination),
                        String.valueOf(count)));
        return convertedMap;
    }

    /**
     * This method checks if its a major currency equivalant
     * or a minor currency equivalant.
     * <p>
     * The logic is to divide the denomination by 100, and if the quotient is greater
     * than 0 then its a major currency and attach the major symbol to the beginning.
     * <p>
     * Else attach the minor symbol to the end
     *
     * @param denom                  the denomination to convert
     * @param majorPartSymbol        the major part symbol
     * @param minorPartSymbol        the minor part symbol
     * @param conversionDenomination the conversion denomination
     * @return a string representation of the currency format
     */
    private String convertPenceToCurr(Integer denom, String majorPartSymbol,
                                      String minorPartSymbol, int conversionDenomination) {
        if (denom / conversionDenomination > 0) {
            return majorPartSymbol + (denom / conversionDenomination);
        } else {
            return denom + minorPartSymbol;
        }
    }


    /**
     * This method computes recursively reducing the amount using the highest denomination available
     * until the amount becomes zero. A hashmap is used to store the data of he denominations used
     * so far and the count of times it has been used.
     *
     * @param amountInPence      the amount in lowest denomination
     * @param denomCountMap      the map to pass around the denomination and the counts
     * @param coinsInPenceSorted the collection of reverse sorted coins
     * @param index              the current index of coinsInPenceSorted
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
     * @return the currency currency models set through spring
     */
    public Collection<CurrencyModel> getCurrencyModels() {
        return currencyModels;
    }

    /**
     * Setter for the currency models
     *
     * @param currencyModels the currency models
     */
    public void setCurrencyModels(Collection<CurrencyModel> currencyModels) {
        this.currencyModels = currencyModels;
    }
}
