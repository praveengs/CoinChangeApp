package com.home.cc.rest.resources;

import com.google.gson.Gson;
import io.dropwizard.testing.junit.DropwizardClientRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Class to test CoinChange restful API
 * Created by prave_000 on 28/11/2015.
 */
public class CoinChangeResourceTest {

    @ClassRule
    public static final DropwizardClientRule dropwizard =
            new DropwizardClientRule(new CoinChangeResource());

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    /*@Test
    public void testForValidInput123p() throws IOException {
        String amount = "123p";
        final URL url = new URL(dropwizard.baseUri() + "/coin-change?amount="+amount);
        final String response = new BufferedReader(new InputStreamReader(url.openStream())).readLine();
        Gson gson = new Gson();
        HashMap<String, String> expectedOutput = new LinkedHashMap<>(4);
        expectedOutput.put("£1","1");
        expectedOutput.put("20p","1");
        expectedOutput.put("2p","1");
        expectedOutput.put("1p","1");
        assertEquals(
                gson.toJson(expectedOutput), response);
        //assertEquals(1, 1);
    }*/

}