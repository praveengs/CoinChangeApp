package com.home.cc.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.home.cc.CoinChangeGenerator;
import com.home.cc.exception.InvalidInputException;
import com.home.cc.rest.exception.BadRequestException;
import com.home.cc.rest.resources.views.AmountBreakDownView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * This is the resources class that will handle the
 * incoming requests
 * <p>
 * Created by prave_000 on 28/11/2015.
 */
@Path("/coin-change")
@Produces("text/html;charset=UTF-8")
public class CoinChangeResource {

    private CoinChangeGenerator coinChangeGenerator;

    public CoinChangeResource() {
        coinChangeGenerator = new CoinChangeGenerator();
    }

    /**
     * the listener for the coin-change url
     *
     * @param amount the amount to convert
     * @return
     * @throws InvalidInputException
     */
    @GET
    @Timed
    public AmountBreakDownView getCoinChange(@QueryParam("amount") String amount) {
        AmountBreakDownView amountBreakDownView;
        String returnString = null;
        String error = null;
        try{
            returnString = coinChangeGenerator.findMinimumChangeString(amount);
        } catch (InvalidInputException e)
        {
            e.printStackTrace();
            error = e.getMessage();
        }
        amountBreakDownView = new AmountBreakDownView(amount, returnString, error);
        return amountBreakDownView;
    }
}
