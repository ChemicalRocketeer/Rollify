package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.ArrayList;
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
        Stack<Long> temp = new Stack<>();
        iterationToken.operate(temp);
        if (temp.size() != 1) throw new InvalidExpressionException();
        long iterations = temp.pop();
        boolean negative = iterations < 0;
        iterations = Math.abs(iterations);
        long result = 0;
        for (int i = 0; i < iterations; i++) {
            result += new Evaluator().evaluate(contents).getResult();
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
