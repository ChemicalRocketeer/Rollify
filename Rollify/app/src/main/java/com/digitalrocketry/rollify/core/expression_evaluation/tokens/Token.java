package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public abstract class Token {


    public abstract void operate(Stack<Long> stack);

    public boolean isNumber() {
        return false;
    }

    public boolean isVariable() { return false; }
}
