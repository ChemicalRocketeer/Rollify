package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.DieToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private static List<Token> fudgeEquation = Arrays.asList(
            new DieToken(new IntegerToken(1), 3, DieToken.KeepRule.ALL, 1),
            new IntegerToken(2),
            Operator.SUB);

    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc)
            throws  InvalidExpressionException {
        if (sc.next() == 'd') {
            sc.skipWhitespace();
            if (sc.hasNextDigit()) {
                long dieType = sc.nextLong();
                DieToken.KeepRule keepRule = DieToken.KeepRule.ALL;
                int keepCount = 1;
                if (sc.hasNext('h')) {
                    sc.next();
                    keepRule = DieToken.KeepRule.HIGHEST;
                    if (sc.hasNextDigit()) {
                        keepCount = (int) sc.nextLong();
                    }
                } else if (sc.hasNext('l')) {
                    sc.next();
                    keepRule = DieToken.KeepRule.LOWEST;
                    if (sc.hasNextDigit()) {
                        keepCount = (int) sc.nextLong();
                    }
                }
                Token dieCount = ExpressionUtils.findCoefficientToken(context);
                context.pushToOutput(new DieToken(dieCount, dieType, keepRule, keepCount));
            } else if (sc.hasNext('f')) {
                // this is a fudge die
                sc.next();
                Token dieCount = ExpressionUtils.findCoefficientToken(context);
                context.pushToOutput(new TokenGroup(dieCount, fudgeEquation));
            } else {
                throw new InvalidExpressionException("No die type specified");
            }
            return true;
        } else {
            return false;
        }
    }
}
