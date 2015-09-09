package com.digitalrocketry.rollify.core.expression_evaluation;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class NumberTokenizer implements Tokenizer {
    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
        if (Character.isDigit(sc.peek())) {
            context.commitToken(new NumberToken(sc.nextLong()));
            return true;
        } else {
            return false;
        }
    }
}
