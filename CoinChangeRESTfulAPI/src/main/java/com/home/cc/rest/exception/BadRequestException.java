package com.home.cc.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The exception class
 *
 * Created by prave_000 on 28/11/2015.
 */
public class BadRequestException extends WebApplicationException {
    public BadRequestException(String message) {
        super(Response
                .status(Response.Status.BAD_REQUEST)
                .header("Warning", message)
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}
