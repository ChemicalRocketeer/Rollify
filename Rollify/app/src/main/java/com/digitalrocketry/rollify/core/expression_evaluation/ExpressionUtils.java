package com.digitalrocketry.rollify.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.NumberToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

import java.util.List;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class ExpressionUtils {

    // this isn't final because tests need to be able to change it
    public static RandomProvider RAND = new DefaultRandomProvider();

    public static final long MAX_EXPRESSION_ITERATIONS = 1000000; // the max number of times we want to repeatedly add a TokenGroup/DieDef
    public static final long MAX_DIE_TYPE = 1000000000; // the largest die type we care to allow

    public static final Token findMultiplierToken(TokenizationContext context) {
        if (context.lastTokenWasnumber()) {
            Token last = context.getLastToken();
            List<Token> tokens = context.getOutputTokens();
            tokens.remove(tokens.size() - 1);
            return last;
        } else {
            return new NumberToken(1);
        }
    }

    public static boolean containsVariable(List<Token> tokens) {
        for (Token toke : tokens) {
            if (toke.isVariable()) {
                return true;
            }
        }
        return false;
    }
}
