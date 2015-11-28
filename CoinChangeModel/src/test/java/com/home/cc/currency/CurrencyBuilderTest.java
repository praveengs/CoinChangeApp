package com.home.cc.currency;

import com.home.cc.exception.InvalidInputException;
import com.home.cc.spring.AppConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

/**
 * Class for testing the currency builder class
 * Created by prave_000 on 25/11/2015.
 */
public class CurrencyBuilderTest {

    private ApplicationContext context = null;
    private CurrencyBuilder currencyBuilder;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        currencyBuilder = context.getBean(CurrencyBuilder.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findValueForValidInput123p() throws InvalidInputException {
        String amount = "123p";
        String currency = "Pounds";
        assertEquals(123, currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm());

    }

    @Test
    public void findValueForValidInputPound12Dot34() throws InvalidInputException {
        String amount = "£12.34";
        String currency = "Pounds";
        assertEquals(1234, currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm());
    }

    @Test
    public void findValueForValidInput432() throws InvalidInputException {
        String amount = "432";
        String currency = "Pounds";
        assertEquals(432, currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm());
    }

    @Test
    public void findValueForValidInputPound16Dot23p() throws InvalidInputException {
        String amount = "£16.23p";
        String currency = "Pounds";
        assertEquals(1623, currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm());

    }

    @Test
    public void findValueForValidInputPound14() throws InvalidInputException {
        String amount = "£14";
        String currency = "Pounds";
        assertEquals(1400, currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm());
    }

    @Test
    public void findValueForValidInputPound23Dot3333() throws InvalidInputException {
        String amount = "£23.333333" ;
        String currency = "Pounds";
        assertEquals(2333, currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm());
    }

    @Test
    public void findValueForValidInput001Dot41p() throws InvalidInputException {
        String amount = "001.41p";
        String currency = "Pounds";
        assertEquals(141, currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm());
    }

    @Test (expected = InvalidInputException.class)
    public void findValueForInValidInput13x() throws InvalidInputException {
        String amount = "13x";
        String currency = "Pounds";
        currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm();
    }

    @Test (expected = InvalidInputException.class)
    public void findValueForInValidInput13pdot02() throws InvalidInputException {
        String amount = "13p.02";
        String currency = "Pounds";
        currencyBuilder.computeCanonicalValue(currency, amount).getAmountInCanonicalForm();
    }
}