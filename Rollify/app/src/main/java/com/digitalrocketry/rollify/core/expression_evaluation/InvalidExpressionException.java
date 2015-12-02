package com.digitalrocketry.rollify.core.expression_evaluation;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 *
 * This exception means there was something wrong with the expression, rendering it invalid.
 */
public class InvalidExpressionException extends Exception {

    public static final String DEFAULT_MESSAGE = "Invalid Expression";

    public InvalidExpressionException() {
        this(DEFAULT_MESSAGE);
    }

    public InvalidExpressionException(String message) {
        super(message);
    }
}
