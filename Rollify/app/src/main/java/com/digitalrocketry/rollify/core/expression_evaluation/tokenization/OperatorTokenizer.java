package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 *
 * Tokenizes operators into OperatorTokens
 */
public class OperatorTokenizer implements Tokenizer {
    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc)
            throws InvalidExpressionException {
        String symbol = String.valueOf(sc.next());
        if (Operator.isDefined(symbol)) {
            Operator op = Operator.get(symbol);
            if (context.lastTokenWasNumber()) {
                context.pushToStack(op);
            } else if (op.equals(Operator.SUB)) {
                // this token might be negation instead of a SUB operator
                sc.skipWhitespace();
                // check expression validity
                if (!sc.hasNext() || Operator.isDefined(sc.peek()))
                    throw new InvalidExpressionException("misplaced operator: " + op);
                long num;
                if (Character.isDigit(sc.peek())) {
                    num = sc.nextLong();
                } else {
                    // if the next token is something other than a number then we want to commit a -1
                    num = 1;
                }
                context.pushToOutput(new IntegerToken(-num));
            } else {
                throw new InvalidExpressionException("misplaced operator: " + op);
            }
            return true;
        } else {
            return false;
        }
    }
}
