package com.home.cc;

import com.home.cc.exception.InvalidInputException;
import com.home.cc.spring.AppConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by prave_000 on 25/11/2015.
 */
public class AmountToCanonicalEqGeneratorTest {

    private ApplicationContext context = null;
    private AmountToCanonicalEqGenerator amountToCanonicalEqGenerator;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        amountToCanonicalEqGenerator = context.getBean(AmountToCanonicalEqGenerator.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findValueForValidInput123p() throws InvalidInputException {
        String amount = "123p";
        assertEquals(123, amountToCanonicalEqGenerator.getCanonicalValue(amount));

    }

    @Test
    public void findValueForValidInputPound12Dot34() throws InvalidInputException {
        String amount = "£12.34";
        assertEquals(1234, amountToCanonicalEqGenerator.getCanonicalValue(amount));
    }

    @Test
    public void findValueForValidInput432() throws InvalidInputException {
        String amount = "432";
        assertEquals(432, amountToCanonicalEqGenerator.getCanonicalValue(amount));
    }

    @Test
    public void findValueForValidInputPound16Dot23p() throws InvalidInputException {
        String amount = "£16.23p";
        assertEquals(1623, amountToCanonicalEqGenerator.getCanonicalValue(amount));

    }

    @Test
    public void findValueForValidInputPound14() throws InvalidInputException {
        String amount = "£14";
        assertEquals(1400, amountToCanonicalEqGenerator.getCanonicalValue(amount));
    }

    @Test
    public void findValueForValidInputPound23Dot3333() throws InvalidInputException {
        String amount = "£23.333333" ;
        assertEquals(2333, amountToCanonicalEqGenerator.getCanonicalValue(amount));
    }

    @Test
    public void findValueForValidInput001Dot41p() throws InvalidInputException {
        String amount = "001.41p";
        assertEquals(141, amountToCanonicalEqGenerator.getCanonicalValue(amount));
    }

    @Test (expected = InvalidInputException.class)
    public void findValueForInValidInput13x() throws InvalidInputException {
        String amount = "13x";
        amountToCanonicalEqGenerator.getCanonicalValue(amount);
    }

    @Test (expected = InvalidInputException.class)
    public void findValueForInValidInput13pdot02() throws InvalidInputException {
        String amount = "13p.02";
        amountToCanonicalEqGenerator.getCanonicalValue(amount);
    }
}