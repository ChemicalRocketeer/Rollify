package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;

import java.util.List;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 *
 * Tokenizes parenthesized groups of tokens into one TokenGroup. This implementation was used
 * instead of the standard shunting-yard algorithm so that random variables in parentheses can have
 * different values if the expression in parentheses is evaluated more than once (such as 4(5d6)
 */
public class ParenTokenizer implements Tokenizer {

    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc)
            throws InvalidExpressionException {
        if (sc.peek() == '(') {
            sc.next();
            List<Token> contents = new TokenizationContext(context, true).tokenize();
            Token multiplier = ExpressionUtils.findCoefficientToken(context);
            context.commitToken(new TokenGroup(multiplier, contents));
            return true;
        } else if (context.isInParentheses() && sc.peek() == ')') {
            sc.next();
            context.finish();
            return true;
        } else {
            return false;
        }
    }
}
