package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class DieToken extends Token {
    private Token dieCount;
    private long dieType;

    public DieToken(Token dieCount, long dieType) {
        this.dieCount = dieCount;
        this.dieType = dieType;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public void operate(Stack<Long> stack) {

    }

    @Override
    public String toString() {
        StringBuilder steve = new StringBuilder();
        steve.append(dieCount.toString());
        steve.append("d");
        steve.append(dieType);
        return steve.toString();
    }
}
