package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;

import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 *
 * Tokenizes parenthesized groups of tokens into one TokenGroup. This implementation was used
 * instead of the standard shunting-yard algorithm so that random variables in parentheses can have
 * different values if the expression in parentheses needs to be evaluated more than once (such as 4(5d6).
 */
public class ParenTokenizer implements Tokenizer {

    // this works because all the nested parentheses will share the same ParenTokenizer instance
    private int nestedParentheses = 0;

    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc)
            throws InvalidExpressionException {
        if (sc.peek() == '(') {
            sc.next();
            nestedParentheses ++;
            List<Token> contents = new TokenizationContext(context).tokenize();
            Token multiplier = ExpressionUtils.findCoefficientToken(context);
            context.pushToOutput(new TokenGroup(multiplier, contents));
            return true;
        } else if (contextIsInParentheses() && sc.peek() == ')') {
            sc.next();
            context.finish();
            nestedParentheses --;
            return true;
        } else {
            return false;
        }
    }

    private boolean contextIsInParentheses() {
        return nestedParentheses > 0;
    }
}
