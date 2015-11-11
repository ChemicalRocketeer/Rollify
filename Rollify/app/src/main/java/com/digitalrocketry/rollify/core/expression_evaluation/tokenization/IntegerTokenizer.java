package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 *
 * Tokenizes Integers into IntegerTokens
 */
public class IntegerTokenizer implements Tokenizer {
    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc)
            throws InvalidExpressionException {
        if (Character.isDigit(sc.peek())) {
            context.pushToOutput(new IntegerToken(sc.nextLong()));
            return true;
        } else {
            return false;
        }
    }
}
