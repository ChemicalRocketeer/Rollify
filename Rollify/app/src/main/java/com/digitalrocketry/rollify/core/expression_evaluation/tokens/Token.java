package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 *
 * Represents a distinct unit of an expression.
 * In 42+6, the tokens are 42, +, and 6.
 * This expression in postfix notation is 42 6 +.
 * The expression is evaluated in a stack machine starting with an empty stack.
 * First, 42 places itself on the stack, then 6 places itself on the stack, then + pulls both
 * of them off, adds them together, and pushes the result onto the stack. So the result is 48.
 *
 * Subclasses using randomness should use ExpressionUtils.RAND, for testing purposes.
 */
public abstract class Token {

    /**
     * Manipulates the stack using whatever operations this token performs.
     *
     * @param stack the stack of arguments to operate on
     */
    public abstract void operate(Stack<Long> stack) throws InvalidExpressionException;

    /**
     * @return true if this Token represents, or evaluates to, a single number
     */
    public boolean isNumber() {
        return false;
    }

    /**
     * @return true if this is a control token, used only during tokenization
     */
    public boolean isControl() {
        return false;
    }

    /**
     * @return true if some element of this Token is generated randomly
     */
    public boolean isVariable() { return false; }
}
