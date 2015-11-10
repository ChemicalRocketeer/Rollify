package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.DieToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 *
 * TODO: Add support for keeping some number of highest and lowest rolls
 *
 * Tokenizes a die definition in the form of [count]d[type] (e.g. 3d20 or (2d6)d20), where [count]
 * is an optional coefficient (any number token works for this), and [type] is an integer
 * representing how many sides the die has.
 */
public class DieDefTokenizer implements Tokenizer {
    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc)
            throws  InvalidExpressionException {
        if (sc.next() == 'd') {
            sc.skipWhitespace();
            if (!Character.isDigit(sc.peek()))
                throw new InvalidExpressionException("No die type specified");
            long dieType = sc.nextLong();
            Token dieCount = ExpressionUtils.findCoefficientToken(context);
            context.commitToken(new DieToken(dieCount, dieType));
            return true;
        } else {
            return false;
        }
    }
}
