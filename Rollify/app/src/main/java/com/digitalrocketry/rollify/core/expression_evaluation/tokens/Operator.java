package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 *
 * Represents an arithmetic operator.
 *
 * You can find operators by their symbols using the isDefined() and get() methods.
 *
 * Subclasses should add themselves to the map using the define() method.
 */
public abstract class Operator extends Token {

    private static final Map<String, Operator> dictionary = new HashMap<>();

    // these operators call define() in their constructors
    public static final Operator ADD = new Add();
    public static final Operator SUB = new Sub();
    public static final Operator MUL = new Mul();
    public static final Operator DIV = new Div();

    /**
     * Adds the given operator to the dictionary so it can be found by its symbol
     * @param op the operator to define
     */
    protected void define(Operator op) {
        dictionary.put(op.getSymbol(), op);
    }

    /**
     * @return whether the given symbol has an Operator associated with it
     */
    public static boolean isDefined(char symbol) {
        return isDefined(String.valueOf(symbol));
    }

    /**
     * @return whether the given symbol has an Operator associated with it
     */
    public static boolean isDefined(String symbol) {
        return dictionary.containsKey(symbol);
    }

    /**
     * @return the Operator associated with the given symbol
     */
    public static Operator get(char symbol) {
        return get(String.valueOf(symbol));
    }

    /**
     * @return the Operator associated with the given symbol
     */
    public static Operator get(String symbol) {
        return dictionary.get(symbol);
    }

    /**
     * Precedence is a measure of how important this Operator is compared to other Operators.
     * Addition and subtraction have a precedence of 1,
     * while multiplication and division have a precedence of 2.
     * For more information, look up the shunting-yard algorithm.
     *
     * @return this Operator's precedence
     */
    public abstract float getPrecedence();

    /**
     * @return the symbol associated with this Operator
     */
    public abstract String getSymbol();

    /**
     * Performs the operation on the numbers in the stack
     *
     * @param stack the stack to use
     */
    @Override
    public abstract void operate(Stack<Long> stack) throws InvalidExpressionException;

    /**
     * @return the symbol representing this operation
     */
    @Override
    public String toString() {
        return getSymbol();
    }

    private static class Add extends Operator {
        Add() {
            if (!isDefined(getSymbol())) define(this);
        }
        @Override
        public float getPrecedence() {
            return 1;
        }
        @Override
        public String getSymbol() {
            return "+";
        }
        @Override
        public void operate(Stack<Long> stack) {
            stack.push(stack.pop() + stack.pop());
        }
    }

    private static class Sub extends Operator {
        Sub() {
            if (!isDefined(getSymbol())) define(this);
        }
        @Override
        public float getPrecedence() {
            return 1;
        }
        @Override
        public String getSymbol() {
            return "-";
        }
        @Override
        public void operate(Stack<Long> stack) {
            long a = stack.pop();
            long b = stack.pop();
            stack.push(b - a);
        }
    }

    private static class Mul extends Operator {
        Mul() {
            if (!isDefined(getSymbol())) define(this);
        }
        @Override
        public float getPrecedence() {
            return 2;
        }
        @Override
        public String getSymbol() {
            return "*";
        }
        @Override
        public void operate(Stack<Long> stack) {
            stack.push(stack.pop() * stack.pop());
        }
    }

    private static class Div extends Operator {
        Div() {
            if (!isDefined(getSymbol())) define(this);
        }
        @Override
        public float getPrecedence() {
            return 2;
        }
        @Override
        public String getSymbol() {
            return "/";
        }
        @Override
        public void operate(Stack<Long> stack) throws InvalidExpressionException {
            long a = stack.pop();
            if (a == 0) throw new InvalidExpressionException("division by zero");
            long b = stack.pop();
            stack.push(b / a);
        }
    }
}
