package com.digitalrocketry.rollify.core.expression_evaluation;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class OperatorTokenizer implements Tokenizer {
    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
        String symbol = String.valueOf(sc.next());
        if (Operator.isDefined(symbol)) {
            Operator op = Operator.get(symbol);
            if (context.lastTokenWasnumber()) {
                context.commitOperator(op);
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
                context.commitToken(new NumberToken(-num));
            } else {
                throw new InvalidExpressionException("misplaced operator: " + op);
            }
            return true;
        } else {
            return false;
        }
    }
}
