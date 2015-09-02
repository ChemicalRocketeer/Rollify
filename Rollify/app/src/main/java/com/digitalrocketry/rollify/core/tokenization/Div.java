package com.digitalrocketry.rollify.core.tokenization;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class Div implements Token {
    @Override
    public void Operate(Stack<Long> stack) {
        long a = stack.pop();
        long b = stack.pop();
        stack.push(b / a);
    }

    @Override
    public String toString() {
        return "/";
    }
}
