package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class TokenGroup extends Token {
    private Token iterationToken;
    private List<Token> contents;

    public TokenGroup(Token iterations, List<Token> contents) {
        this.iterationToken = iterations;
        this.contents = contents;
    }

    @Override
    public void operate(Stack<Long> stack) {
        long iterations = new Evaluator().evaluate(iterationToken).getResult();
        boolean negative = iterations < 0;
        iterations = Math.abs(iterations);
        long result = 0;
        if (ExpressionUtils.containsVariable(contents)) {
            // since there is a variable in this expression, we have to repeatedly evaluate and add
            // the expression to itself. Every iteration can have a different result.
            if (iterations > ExpressionUtils.MAX_EXPRESSION_ITERATIONS) throw new InvalidExpressionException(Long.toString(iterations) + " is too many to count");
            for (int i = 0; i < iterations; i++) {
                result += new Evaluator().evaluate(contents).getResult();
            }
        } else {
            // There is no variable in this expression, so we can just evaluate it once and multiply it with the iteration count
            result = iterations * new Evaluator().evaluate(contents).getResult();
        }
        if (negative)
            result = -result;
        stack.push(result);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isVariable() {
        return ExpressionUtils.containsVariable(contents);
    }

    @Override
    public String toString() {
        StringBuilder steve = new StringBuilder();
        steve.append(iterationToken.toString());
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
