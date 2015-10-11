package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;

import java.util.List;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class ParenTokenizer implements Tokenizer {
    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
        if (sc.peek() == '(') {
            sc.next();
            List<Token> contents = new TokenizationContext(context, true).tokenize();
            Token multiplier = ExpressionUtils.findMultiplierToken(context);
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
