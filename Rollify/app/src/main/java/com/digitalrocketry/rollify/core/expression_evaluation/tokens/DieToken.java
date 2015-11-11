package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 *
 * A token representing a die roll or combination of rolls.
 */
public class DieToken extends Token {

    public static final long MAX_DIE_TYPE = 1000000000; // the largest die type we care to represent

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
    public TYPE getType() {
        return TYPE.NUMBER;
    }

    @Override
    public boolean isVariable() { return true; }

    /**
     * Rolls the dice specified in the die definition, combines them, and pushes the result onto the stack
     */
    @Override
    public void operate(Stack<Long> stack) throws InvalidExpressionException {
        if (dieType < 1 || dieType > MAX_DIE_TYPE) throw new InvalidExpressionException("invalid die");
        long iterations = new Evaluator().evaluate(dieCount).getResult();
        // if count is negative, we will calculate it as if it were positive and then negate the result.
        // i.e. -3d4 is treated as -(3d4)
        boolean negative = iterations < 0;
        iterations = Math.abs(iterations);
        if (iterations > ExpressionUtils.MAX_OPERATION_ITERATIONS) throw new InvalidExpressionException("too many dice");
        long[] results = new long[(int) iterations];
        for (int i = 0; i < results.length; i++) {
            results[i] = ExpressionUtils.RAND.nextLong(1, dieType);
        }
        long total = 0;
        for (long die : results) {
            total += die;
        }
        if (negative) {
            total = -total;
        }
        stack.push(total);
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
