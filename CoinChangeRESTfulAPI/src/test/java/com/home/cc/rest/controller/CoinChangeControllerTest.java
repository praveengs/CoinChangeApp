package com.home.cc.rest.controller;

import com.google.gson.Gson;
import com.home.cc.rest.config.ApplicationConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

/**
 * For testing the main application
 *
 * Created by prave_000 on 28/11/2015.
 */
public class CoinChangeControllerTest {
    @Rule
    public final DropwizardAppRule<ApplicationConfiguration> RULE =
            new DropwizardAppRule<>(CoinChangeController.class,
                    ResourceHelpers.resourceFilePath("service.yml"));

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

   /* @Test
    public void testForValidInput123p() {
        String amount = "123p";
        Client client = new JerseyClientBuilder().build();
        String result = client.target(
                String.format("http://localhost:%d/coin-change", RULE.getLocalPort())
        ).queryParam("amount", amount).request().get(String.class);
        Gson gson = new Gson();
        HashMap<String, String> expectedOutput = new LinkedHashMap<>(4);
        expectedOutput.put("£1","1");
        expectedOutput.put("20p","1");
        expectedOutput.put("2p","1");
        expectedOutput.put("1p","1");
        assertEquals(
                gson.toJson(expectedOutput), result);
    }*/
}
