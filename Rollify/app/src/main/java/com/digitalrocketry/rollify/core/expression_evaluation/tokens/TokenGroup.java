package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 *
 * A group of tokens that evaluates to one value. It has a coefficient, which can be an IntegerToken
 * of value 1 if you just want a TokenGroup a coefficient (anything multiplied by 1 is itself).
 */
public class TokenGroup extends Token {
    private Token coefficientToken;
    private List<Token> contents;

    public TokenGroup(Token coefficient, List<Token> contents) {
        this.coefficientToken = coefficient;
        this.contents = contents;
    }

    /**
     * Evaluates the expression as many times as specified by the coefficient, and pushes the total on the stack
     *
     * @param stack the stack to use
     * @throws InvalidExpressionException if the expression is invalid
     */
    @Override
    public void operate(Stack<Long> stack) throws InvalidExpressionException {
        long coefficient = new Evaluator().evaluate(coefficientToken).getResult();
        boolean negative = coefficient < 0;
        coefficient = Math.abs(coefficient);
        long result = 0;
        if (ExpressionUtils.containsRandomVariable(contents)) {
            // since there is a random variable in this expression, we have to repeatedly evaluate and add
            // the expression to itself. Every iteration can have a different result.
            if (coefficient > ExpressionUtils.MAX_OPERATION_ITERATIONS)
                throw new InvalidExpressionException(Long.toString(coefficient) + " is too many to count");
            for (int i = 0; i < coefficient; i++) {
                result += new Evaluator().evaluate(contents).getResult();
            }
        } else {
            // There is no randomness in this expression, so we can just evaluate it once and multiply it by the coefficient
            result = coefficient * new Evaluator().evaluate(contents).getResult();
        }
        if (negative)
            result = -result;
        stack.push(result);
    }

    /**
     * @return true
     */
    @Override
    public boolean isNumber() {
        return true;
    }

    /**
     * @return true if any part of this TokenGroup is random
     */
    @Override
    public boolean isVariable() {
        return coefficientToken.isVariable() || ExpressionUtils.containsRandomVariable(contents);
    }

    /**
     * @return toString() of the coefficient, followed by the toString() of each of the contents,
     *         surrounded by parentheses, separated by spaces.
     *         [coefficient]([item1] [item2] [item3]...)
     */
    @Override
    public String toString() {
        StringBuilder steve = new StringBuilder();
        steve.append(coefficientToken.toString());
        steve.append('(');
        Iterator<Token> it = contents.iterator();
        if (it.hasNext()) {
            steve.append(it.next().toString());
        }
        while (it.hasNext()) {
            steve.append(' ');
            steve.append(it.next().toString());
        }
        steve.append(')');
        return steve.toString();
    }
}
