package com.home.cc;

import com.google.gson.Gson;
import com.home.cc.exception.InvalidInputException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Class for testing the Coin Change generator class
 *
 * Created by prave_000 on 25/11/2015.
 */
public class CoinChangeGeneratorTest {

    private CoinChangeGenerator coinChangeGenerator;

    @Before
    public void setUp() throws Exception {
        coinChangeGenerator = new CoinChangeGenerator();
    }

    @After
    public void tearDown() throws Exception {
        coinChangeGenerator = null;
    }

    @Test
    public void testCreateCoinGeneratorInstance() {
        assertNotNull(coinChangeGenerator);
    }

    @Test
    public void countChangeForValidInput123p() throws InvalidInputException {
        String amount = "123p";
        String currency = "Pounds";
        assertEquals("1 x £1, 1 x 20p, 1 x 2p, 1 x 1p", coinChangeGenerator.findMinimumChangeString(currency, amount));
    }

    @Test
    public void countChangeForValidInputPound12Dot34() throws InvalidInputException {
        String amount = "£12.34";
        String currency = "Pounds";
        assertEquals("6 x £2, 1 x 20p, 1 x 10p, 2 x 2p", coinChangeGenerator.findMinimumChangeString(currency, amount));
    }

    @Test
    public void countChangeForValidInput432() throws InvalidInputException {
        String amount = "432";
        String currency = "Pounds";
        assertEquals("2 x £2, 1 x 20p, 1 x 10p, 1 x 2p", coinChangeGenerator.findMinimumChangeString(currency, amount));
    }

    @Test
    public void countChangeForValidInputPound16Dot23p() throws InvalidInputException {
        String amount = "£16.23p";
        String currency = "Pounds";
        assertEquals("8 x £2, 1 x 20p, 1 x 2p, 1 x 1p", coinChangeGenerator.findMinimumChangeString(currency, amount));
    }

    @Test
    public void countChangeForValidInput123pJSON() throws InvalidInputException {
        String currency = "Pounds";
        Gson gson = new Gson();
        String amount = "123p";
        HashMap<String, String> expectedOutput = new LinkedHashMap<>(4);
        expectedOutput.put("£1","1");
        expectedOutput.put("20p","1");
        expectedOutput.put("2p","1");
        expectedOutput.put("1p","1");
        assertEquals(
                gson.toJson(expectedOutput),
                coinChangeGenerator.findMinimumChangeJSON(currency, amount));
    }

    @Test (expected = InvalidInputException.class)
    public void findValueForInValidInput13pdot02() throws InvalidInputException {
        String amount = "13p.02";
        String currency = "Pounds";
        coinChangeGenerator.findMinimumChangeString(currency, amount);
    }

    @Test (expected = InvalidInputException.class)
    public void findValueForInValidInput£p() throws InvalidInputException {
        String amount = "£p";
        String currency = "Pounds";
        coinChangeGenerator.findMinimumChangeString(currency, amount);
    }
}