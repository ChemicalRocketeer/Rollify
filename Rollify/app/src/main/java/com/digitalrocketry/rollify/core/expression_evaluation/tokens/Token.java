package com.digitalrocketry.rollify.core.expression_evaluation.tokens;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 *
 * Represents a distinct part of an expression. In 42+6, the tokens are 42, +, and 6.
 *
 * Subclasses using randomness should use ExpressionUtils.RAND
 */
public abstract class Token {

    /**
     * Manipulates the stack using whatever operations this token performs
     * @param stack the stack to use
     */
    public abstract void operate(Stack<Long> stack) throws InvalidExpressionException;

    /**
     * @return true if this Token represents, or evaluates to, a single number
     */
    public boolean isNumber() {
        return false;
    }

    /**
     * @return true if some element of this Token is generated randomly
     */
    public boolean isVariable() { return false; }
}
