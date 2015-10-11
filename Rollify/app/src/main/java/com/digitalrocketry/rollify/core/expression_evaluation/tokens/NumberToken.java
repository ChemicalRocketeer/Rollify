package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class NumberToken extends Token {
    private long num;

    public NumberToken(long num) {
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
