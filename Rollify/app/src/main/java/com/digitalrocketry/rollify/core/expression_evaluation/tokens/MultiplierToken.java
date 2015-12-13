package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 12/5/2015.
 */
public class MultiplierToken extends Token {

    public enum KeepRule {
        HIGHEST, LOWEST, ALL
    }

    private Token coefficient;
    private Token body;
    private KeepRule keepRule;
    private int keepCount;

    public MultiplierToken(Token coefficient, Token body) {
        this(coefficient, body, KeepRule.ALL, 1);
    }

    public MultiplierToken(Token coefficient, Token body, KeepRule keepRule, int keepCount) {
        this.coefficient = coefficient;
        this.body = body;
        this.keepRule = keepRule;
        this.keepCount = keepCount;
    }

    @Override
    public void operate(Stack<Long> stack) throws InvalidExpressionException {
        int iterations = (int) new Evaluator().evaluate(coefficient).getResult();
        boolean negative = iterations < 0;
        iterations = Math.abs(iterations);
        if (iterations > ExpressionUtils.MAX_OPERATION_ITERATIONS) {
            throw new InvalidExpressionException(iterations + " is too much");
        }
        long result = 0;
        if (body.isVariable()) {
            // get all the results
            long[] results = new long[iterations];
            Evaluator eva = new Evaluator();
            for (int i = 0; i < iterations; i++) {
                results[i] = eva.evaluate(body).getResult();
            }
            // implement keep rules
            Arrays.sort(results);
            int start = 0, end = results.length;
            if (keepRule == KeepRule.HIGHEST) {
                start = end - keepCount - 1;
            } else if (keepRule == KeepRule.LOWEST) {
                end = keepCount;
            }
            // add up the results
            for (int i = start; i < end; i++) {
                result += results[i];
            }
        } else {
            // the body has no randomness, so we can just run the evaluation once and multiply it
            result = new Evaluator().evaluate(body).getResult() * iterations;
        }
        if (negative) {
            result = -result;
        }
        stack.push(result);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isVariable() {
        return coefficient.isVariable() || body.isVariable();
    }

    @Override
    public String toString() {
        StringBuilder steve = new StringBuilder();
        steve.append(coefficient.toString());
        // A space is used here because it allows maximum clarity of information.
        // Parentheses are already used by TokenGroup, and wouldn't work because 2d4
        // would become 2(d4) and then if that were tokenized again would become 2((d4)) etc.
        // The space is needed because without it, two numbers might end up next to each other
        // and could be interpreted as one larger number.
        steve.append(" ");
        steve.append(body.toString());
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
