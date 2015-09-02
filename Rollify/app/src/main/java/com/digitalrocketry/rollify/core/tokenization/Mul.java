package com.digitalrocketry.rollify.core.tokenization;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class Mul implements Token{
    @Override
    public void Operate(Stack<Long> stack) {
        stack.push((stack.pop() * stack.pop()));
    }

    @Override
    public String toString() {
        return "*";
    }
}
