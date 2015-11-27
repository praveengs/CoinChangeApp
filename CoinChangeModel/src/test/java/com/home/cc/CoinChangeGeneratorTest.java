package com.home.cc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
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
    public void countChangeForValidInput123p() {
        String amount = "123p";
    }

    @Test
    public void countChangeForValidInputPound12Dot34() {
        String amount = "£12.34";
    }

    @Test
    public void countChangeForValidInput432() {
        String amount = "432";
    }

    @Test
    public void countChangeForValidInputPound16Dot23p() {
        String amount = "£16.23p";
    }
}