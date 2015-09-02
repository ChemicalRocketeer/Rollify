package com.digitalrocketry.rollify.core.tokenization;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class Number implements Token {
    private long num;

    public Number(long num) {
        this.num = num;
    }

    @Override
    public void Operate(Stack<Long> stack) {
        stack.push(num);
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }
}
