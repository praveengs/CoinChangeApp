package com.home.cc.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.home.cc.CoinChangeGenerator;
import com.home.cc.exception.InvalidInputException;
import com.home.cc.rest.exception.BadRequestException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * This is the resources class that will handle the
 * incoming requests
 * <p>
 * Created by prave_000 on 28/11/2015.
 */
@Path("/coin-change")
@Produces(MediaType.APPLICATION_JSON)
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
    public String getCoinChange(@QueryParam("amount") String amount) {
        String returnString;
        try{
            returnString = coinChangeGenerator.findMinimumChangeJSON(amount);
        } catch (InvalidInputException e)
        {
            throw new BadRequestException(e.getMessage());
        }
        return returnString;
    }
}
