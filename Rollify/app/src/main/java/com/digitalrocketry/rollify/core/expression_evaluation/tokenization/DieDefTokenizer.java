package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.DieToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

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
            Token dieCount = ExpressionUtils.findMultiplierToken(context);
            context.commitToken(new DieToken(dieCount, dieType));
            return true;
        } else {
            return false;
        }
    }
}
