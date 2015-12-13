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
        context.pushToOutput(new IntegerToken(42));
        Token multiplier = context.tryFindCoefficientToken();
        assertNotEquals(null, multiplier);
        assertEquals("42", multiplier.toString());
    }

    @Test
    public void testFindMultiplierTokenWithoutMultiplier() throws Exception {
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        Token multiplier = context.tryFindCoefficientToken();
        assertEquals(null, multiplier);
    }

    @Test
    public void testFindMultiplierTokenWithNonNumber() throws Exception {
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        context.pushToStack(Operator.ADD);
        Token multiplier = context.tryFindCoefficientToken();
        assertEquals(null, multiplier);
    }
}