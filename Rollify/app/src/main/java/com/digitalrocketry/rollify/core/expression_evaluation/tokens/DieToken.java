package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 *
 * A token representing a die roll or combination of rolls.
 */
public class DieToken extends Token {

    public static final long MAX_DIE_TYPE = 1000000000; // the largest die type we care to represent

    private long dieType;

    public DieToken(long dieType) {
        this.dieType = dieType;
    }

    /**
     * Rolls the dice specified in the die definition, combines them, and pushes the result onto the stack
     */
    @Override
    public void operate(Stack<Long> stack) throws InvalidExpressionException {
        if (dieType < 1 || dieType > MAX_DIE_TYPE) throw new InvalidExpressionException("invalid die");
        stack.push(ExpressionUtils.RAND.nextLong(1, dieType));
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isVariable() { return true; }

    @Override
    public String toString() {
        StringBuilder steve = new StringBuilder();
        steve.append("d");
        steve.append(dieType);
        return steve.toString();
    }
}
