package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 *
 * A Token with a single numerical value
 */
public class IntegerToken extends Token {
    private long num;

    public IntegerToken(long num) {
        this.num = num;
    }

    @Override
    public void operate(Stack<Long> stack) {
        stack.push(num);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }
}
