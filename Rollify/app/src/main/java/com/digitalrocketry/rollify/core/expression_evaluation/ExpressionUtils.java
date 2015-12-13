package com.digitalrocketry.rollify.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

import java.util.Iterator;
import java.util.List;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 *
 * Utilities for evaluation and tokenization classes.
 */
public class ExpressionUtils {

    // this isn't final because tests need to be able to change it
    public static RandomProvider RAND = new DefaultRandomProvider();

    // the max number of times we want to repeatedly perform operations like additions and expression evaluations.
    // Ultimately it is up to individual classes to enforce this.
    public static final long MAX_OPERATION_ITERATIONS = 1000000;

    /**
     * If your token can take a coefficient (e.g. 3x where 3 is the coefficient) then this method
     * will look for a suitable token, and pop it out of the context and return it for you to use.
     * If there is no suitable token (if the last token was not a number) then it will return an
     * IntegerToken of value 1, because multiplying by 1 has no effect.
     * This method does not return null.
     *
     * @param context the TokenizationContext to find a coefficient from
     * @return the coefficient, or an IntegerToken of value 1
     */
    public static Token findCoefficientToken(TokenizationContext context) {
        if (context.lastTokenWasNumber()) {
            return context.popOutput();
        } else {
            return new IntegerToken(1);
        }
    }

    /**
     * @param tokens a list of Tokens
     * @return true if one of the tokens is a variable that can have a random value (like a DieToken)
     */
    public static boolean containsRandomVariable(List<Token> tokens) {
        for (Token toke : tokens) {
            if (toke.isVariable()) {
                return true;
            }
        }
        return false;
    }

    public static String toString(List<Token> tokens) {
        StringBuilder steve = new StringBuilder();
        Iterator<Token> it = tokens.iterator();
        if (it.hasNext()) {
            steve.append(it.next());
        }
        while (it.hasNext()) {
            steve.append(' ');
            steve.append(it.next().toString());
        }
        return steve.toString();
    }
}
