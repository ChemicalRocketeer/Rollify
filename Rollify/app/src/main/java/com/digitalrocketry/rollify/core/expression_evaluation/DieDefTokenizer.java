package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.List;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class DieDefTokenizer implements Tokenizer {
    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
        if (sc.next() == 'd') {
            sc.skipWhitespace();
            if (!Character.isDigit(sc.peek()))
                throw new InvalidExpressionException("No die type specified");
            long dieType = sc.nextLong();
            // if there was a number before the diedef, it is the diecount. Otherwise the diecount is 1
            Token dieCount;
            if (context.lastTokenWasnumber()) {
                dieCount = context.getLastToken();
                List<Token> outputTokens = context.getOutputTokens();
                outputTokens.remove(outputTokens.size() - 1);
            } else {
                dieCount = new NumberToken(1);
            }
            context.commitToken(new DieToken(dieCount, dieType));
            return true;
        } else {
            return false;
        }
    }
}
