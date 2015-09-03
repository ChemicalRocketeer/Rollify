package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class Operator implements Token {

    private static final Map<String, Operator> dictionary = new HashMap<>();

    public static final Operator ADD = new Operator("+", 1f, new Token() {
        @Override
        public void Operate(Stack<Long> stack) {
            stack.push(stack.pop() + stack.pop());
        }
    });

    public static final Operator SUB = new Operator("-", 1f, new Token() {
        @Override
        public void Operate(Stack<Long> stack) {
            long a = stack.pop();
            long b = stack.pop();
            stack.push(b - a);
        }
    });

    public static final Operator MUL = new Operator("*", 2f, new Token() {
        @Override
        public void Operate(Stack<Long> stack) {
            stack.push(stack.pop() * stack.pop());
        }
    });

    public static final Operator DIV = new Operator("/", 2f, new Token() {
        @Override
        public void Operate(Stack<Long> stack) {
            long a = stack.pop();
            long b = stack.pop();
            stack.push(b / a);
        }
    });

    public static boolean contains(String symbol) {
        return dictionary.containsKey(symbol);
    }

    public static Operator get(String symbol) {
        return dictionary.get(symbol);
    }

    private String symbol;
    private float precedence;
    private Token operation;

    public Operator(String symbol, float precedence, Token operation) {
        if ((operation instanceof Operator)) throw new AssertionError();
        this.symbol = symbol;
        this.precedence = precedence;
        this.operation = operation;
        dictionary.put(symbol, this);
    }

    public float getPrecedence() {
        return precedence;
    }

    @Override
    public void Operate(Stack<Long> stack) {
        operation.Operate(stack);
    }

    @Override
    public String toString() {
        return symbol;
    }
}
