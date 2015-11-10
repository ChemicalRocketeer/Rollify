package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/9/2015.
 */
public class ExpressionUtilsTest {

    @Test
    public void testFindMultiplierToken() throws Exception {
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        context.commitToken(new IntegerToken(42));
        Token multiplier = ExpressionUtils.findCoefficientToken(context);
        assertEquals("42", multiplier.toString());
    }

    @Test
    public void testFindMultiplierTokenWithoutMultiplier() throws Exception {
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        Token multiplier = ExpressionUtils.findCoefficientToken(context);
        assertEquals("1", multiplier.toString());
    }

    @Test
    public void testFindMultiplierTokenWithNonNumber() throws Exception {
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        context.commitOperator(Operator.ADD);
        Token multiplier = ExpressionUtils.findCoefficientToken(context);
        assertEquals("1", multiplier.toString());
    }
}