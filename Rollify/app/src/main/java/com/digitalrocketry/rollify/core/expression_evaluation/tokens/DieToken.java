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

    public enum KeepRule {
        HIGHEST, LOWEST, ALL
    }

    public static final long MAX_DIE_TYPE = 1000000000; // the largest die type we care to represent

    private Token dieCount;
    private long dieType;
    private KeepRule keepRule;
    private int keepCount;

    public DieToken(Token dieCount, long dieType, KeepRule keepRule, int keepCount) {
        this.dieCount = dieCount;
        this.dieType = dieType;
        this.keepRule = keepRule;
        this.keepCount = keepCount;
    }

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

        // implement the keep rule
        long[] resultsForCounting = results; // used for counting, not necessarily in the original order
        int start = 0, end = results.length;
        if (keepRule == KeepRule.HIGHEST || keepRule == KeepRule.LOWEST) {
            results = Arrays.copyOf(results, results.length);
            Arrays.sort(results);
            if (keepRule == KeepRule.LOWEST) {
                end = keepCount - 1;
            } else {
                start = end - keepCount - 1;
            }
        }

        long total = 0;
        for (int i = start; i < end; i++) {
            total += results[i];
        }
        if (negative) {
            total = -total;
        }
        stack.push(total);
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
        steve.append(dieCount.toString());
        steve.append("d");
        steve.append(dieType);
        switch (keepRule) {
            case HIGHEST:
                steve.append("h");
                steve.append(keepCount);
                break;
            case LOWEST:
                steve.append("l");
                steve.append(keepCount);
                break;
        }
        return steve.toString();
    }
}
