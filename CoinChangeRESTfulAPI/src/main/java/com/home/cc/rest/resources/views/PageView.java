package com.home.cc.rest.resources.views;

import io.dropwizard.views.View;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by prave_000 on 28/11/2015.
 */
public class PageView extends View {

    private List<String> currencies;

    /**
     * Creates a new view.
     *
     * @param currencies  the content of the message
     */
    public PageView(List<String> currencies) {
        super("frontpage.ftl", StandardCharsets.UTF_8);
        setCurrencies(currencies);
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }
}
