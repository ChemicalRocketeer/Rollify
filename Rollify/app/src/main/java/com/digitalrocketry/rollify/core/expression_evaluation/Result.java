package com.digitalrocketry.rollify.core.expression_evaluation;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 *
 * Contains the result of a die roll, along with any relevant information about the result.
 */
public class Result {
    private long result;

    public Result(long value) {
        this.result = value;
    }

    public long getResult() {
        return result;
    }
}
