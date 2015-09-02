package com.digitalrocketry.rollify.core.tokenization;

import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class TokenGroup implements Token {
    private Token iterations;
    private List<Token> contents;

    public TokenGroup(Token iterations, List<Token> contents) {
        this.iterations = iterations;
        this.contents = contents;
    }

    @Override
    public void Operate(Stack<Long> stack) {
    }

    @Override
    public String toString() {
        StringBuilder steve = new StringBuilder();
        steve.append(iterations.toString());
        for (Token t : contents) {
            steve.append(" ");
            steve.append(t.toString());
        }
        return steve.toString();
    }
}
