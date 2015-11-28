package com.home.cc.rest.resources;

import com.home.cc.CoinChangeGenerator;
import com.home.cc.rest.resources.views.PageView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the resource for the landing page
 *
 * Created by prave_000 on 28/11/2015.
 */
@Path("/")
@Produces("text/html;charset=UTF-8")
public class FrontPageResource {

    private CoinChangeGenerator coinChangeGenerator;

    public FrontPageResource() {
        coinChangeGenerator = new CoinChangeGenerator();
    }

    @GET
    public PageView getFrontPage() {
        List<String> currencies =
                coinChangeGenerator.retrieveAllCurrencieNames();
        return new PageView(currencies);
    }
}
