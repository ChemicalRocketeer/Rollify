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
        if (dieType < 1 || dieType > Utils.MAX_DIE_TYPE) throw new InvalidExpressionException("invalid die");
        Stack<Long> temp = new Stack<Long>();
        dieCount.operate(temp);
        if (temp.size() != 1) throw new InvalidExpressionException();
        long count = temp.pop();
        boolean negative = false;
        // if count is negative, we will calculate it as if it were positive and then negate the result.
        // i.e. -3d4 is treated as -(3d4)
        if (count < 0) {
            negative = true;
            count = -count;
        }
        if (count > Utils.MAX_DIE_COUNT) throw new InvalidExpressionException("too many dice");
        long[] results = new long[(int) count];
        for (int i = 0; i < results.length; i++) {
            results[i] = Utils.RAND.nextLong() % dieType + 1;
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
