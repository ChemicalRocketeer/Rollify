package com.digitalrocketry.rollify.core.expression_evaluation;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class InvalidExpressionException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Unknown Error";

    public InvalidExpressionException() {
        this(DEFAULT_MESSAGE);
    }

    public InvalidExpressionException(String message) {
        super(message);
    }
}
