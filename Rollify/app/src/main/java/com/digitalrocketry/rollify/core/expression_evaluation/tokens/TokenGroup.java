package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.util.Arrays;
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

    private List<Token> contents;

    public TokenGroup(Token... contents) {
        this(Arrays.asList(contents));
    }

    public TokenGroup(List<Token> contents) {
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
        stack.push(new Evaluator().evaluate(contents).getResult());
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
        return ExpressionUtils.containsRandomVariable(contents);
    }

    /**
     * @return toString() of each of the contents,
     *         surrounded by parentheses, separated by spaces.
     *         ([item1] [item2] [item3]...)
     */
    @Override
    public String toString() {
        StringBuilder steve = new StringBuilder();
        steve.append('(');
        steve.append(ExpressionUtils.toString(contents));
        steve.append(')');
        return steve.toString();
    }
}
