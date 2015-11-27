package com.home.cc.exception;

/**
 * Created by prave_000 on 25/11/2015.
 */
public class InvalidInputException extends  Exception {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputException(Throwable cause) {
        super(cause);
    }
}
