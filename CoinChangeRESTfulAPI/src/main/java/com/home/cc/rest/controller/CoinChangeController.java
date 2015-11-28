package com.home.cc.rest.controller;

import com.home.cc.rest.config.ApplicationConfiguration;
import com.home.cc.rest.resources.CoinChangeResource;
import com.home.cc.rest.resources.FrontPageResource;
import io.dropwizard.Application;
import io.dropwizard.Bundle;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 * This is the entry point into this webservice
 *
 * Created by prave_000 on 28/11/2015.
 */
public class CoinChangeController extends Application<ApplicationConfiguration> {

    /**
     * Initializes the application bootstrap.
     *
     * @param bootstrap the application bootstrap
     */
    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<ApplicationConfiguration>());
    }

    /**
     * When the application runs, this is called after the {@link Bundle}s are run. Override it to add
     * providers, resources, etc. for your application.
     *
     * @param configuration the parsed {@link Configuration} object
     * @param environment   the application's {@link Environment}
     * @throws Exception if something goes wrong
     */
    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
        CoinChangeResource coinChangeResource = new CoinChangeResource();
        FrontPageResource frontPageResource = new FrontPageResource();
        environment.jersey().register(coinChangeResource);
        environment.jersey().register(frontPageResource);
    }

    /**
     * Main method to run standalone
     *
     * @param args the incoming arguments, ideally "server service.yml"
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new CoinChangeController().run(args);
    }
}
