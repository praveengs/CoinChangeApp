package com.home.cc.rest.resources.views;

import io.dropwizard.views.View;

import java.nio.charset.StandardCharsets;

/**
 * Created by prave_000 on 28/11/2015.
 */
public class AmountBreakDownView extends View{

    private String amount;
    private String breakDown;
    private String error;
    /**
     * Creates a new view.
     *
     */
    public AmountBreakDownView(String amount, String amountBreakDown, String error) {
        super("amountview.ftl", StandardCharsets.UTF_8);
        setAmount(amount);
        setBreakDown(amountBreakDown);
        setError(error);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBreakDown() {
        return breakDown;
    }

    public void setBreakDown(String breakDown) {
        this.breakDown = breakDown;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
