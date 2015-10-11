package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class Operator extends Token {

    private static final Map<String, Operator> dictionary = new HashMap<>();

    public static final Operator ADD = new Operator("+", 1f, new Token() {
        @Override
        public void operate(Stack<Long> stack) {
            stack.push(stack.pop() + stack.pop());
        }
    });

    public static final Operator SUB = new Operator("-", 1f, new Token() {
        @Override
        public void operate(Stack<Long> stack) {
            long a = stack.pop();
            long b = stack.pop();
            stack.push(b - a);
        }
    });

    public static final Operator MUL = new Operator("*", 2f, new Token() {
        @Override
        public void operate(Stack<Long> stack) {
            stack.push(stack.pop() * stack.pop());
        }
    });

    public static final Operator DIV = new Operator("/", 2f, new Token() {
        @Override
        public void operate(Stack<Long> stack) {
            long a = stack.pop();
            long b = stack.pop();
            stack.push(b / a);
        }
    });

    public static boolean isDefined(char symbol) {
        return isDefined(String.valueOf(symbol));
    }

    public static boolean isDefined(String symbol) {
        return dictionary.containsKey(symbol);
    }

    public static Operator get(char symbol) {
        return get(String.valueOf(symbol));
    }

    public static Operator get(String symbol) {
        return dictionary.get(symbol);
    }

    private String symbol;
    private float precedence;
    private Token operation;

    private Operator(String symbol, float precedence, Token operation) {
        assert !(operation instanceof Operator);
        this.symbol = symbol;
        this.precedence = precedence;
        this.operation = operation;
        dictionary.put(symbol, this);
    }

    public float getPrecedence() {
        return precedence;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public void operate(Stack<Long> stack) {
        operation.operate(stack);
    }

    @Override
    public String toString() {
        return symbol;
    }
}
