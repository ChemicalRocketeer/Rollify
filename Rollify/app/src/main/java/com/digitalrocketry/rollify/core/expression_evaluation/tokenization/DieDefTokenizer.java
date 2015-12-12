package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import android.content.Context;
import android.support.annotation.Keep;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.DieToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.MultiplierToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.MultiplierToken.KeepRule;

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

    private static TokenGroup fudgeEquation = new TokenGroup(Arrays.asList(
            new DieToken(3),
            new IntegerToken(2),
            Operator.SUB));

    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc)
            throws  InvalidExpressionException {
        if (sc.next() == 'd') {
            sc.skipWhitespace();
            Token dieCount = ExpressionUtils.tryFindCoefficientToken(context);
            Token dieToken = findDieType(sc);
            if (dieCount != null) {
                // there is a dieCount, so we need to wrap the dieToken in a MultiplierToken
                dieToken = findMultiplier(sc, dieToken, dieCount);
            }
            context.pushToOutput(dieToken);
            return true;
        } else {
            return false;
        }
    }

    private Token findDieType(StringScanner sc) throws InvalidExpressionException {
        if (sc.hasNextDigit()) {
            long dieType = sc.nextLong();
            return new DieToken(dieType);
        } else if (sc.hasNext('f')) {
            // this is a fudge die
            sc.next();
            return fudgeEquation;
        } else {
            throw new InvalidExpressionException("No die type specified");
        }
    }

    private MultiplierToken findMultiplier(StringScanner sc, Token dieToken, Token dieCount)
            throws InvalidExpressionException {
        KeepRule keepRule = findKeepRule(sc);
        int keepCount = 1;
        if (keepRule != KeepRule.ALL) {
            if (sc.hasNextDigit()) {
                keepCount = (int) sc.nextLong();
            } else {
                throw new InvalidExpressionException("No die count specified");
            }
        }
        return new MultiplierToken(dieCount, dieToken, keepRule, keepCount);
    }

    private MultiplierToken.KeepRule findKeepRule(StringScanner sc) {
        if (sc.hasNext('h')) {
            sc.next();
            return KeepRule.HIGHEST;
        } else if (sc.hasNext('l')) {
            sc.next();
            return KeepRule.LOWEST;
        } else {
            return KeepRule.ALL;
        }
    }
}
