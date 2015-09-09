package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.NumberToken;
import com.digitalrocketry.rollify.core.expression_evaluation.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.Tokenizer;

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
        context.commitToken(new NumberToken(42));
        Token multiplier = ExpressionUtils.findMultiplierToken(context);
        assertEquals("42", multiplier.toString());
    }

    @Test
    public void testFindMultiplierTokenWithoutMultiplier() throws Exception {
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        Token multiplier = ExpressionUtils.findMultiplierToken(context);
        assertEquals("1", multiplier.toString());
    }

    @Test
    public void testFindMultiplierTokenWithNonNumber() throws Exception {
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        context.commitOperator(Operator.ADD);
        Token multiplier = ExpressionUtils.findMultiplierToken(context);
        assertEquals("1", multiplier.toString());
    }
}