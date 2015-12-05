package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.DieDefTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.IntegerTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/9/2015.
 */
public class DieDefTokenizerTest {

    private DieDefTokenizer toke;

    @Before
    public void before() {
        toke = new DieDefTokenizer();
    }

    @Test
    public void testTokenizeFudgeDice() throws Exception {
        TokenizationContext context = new TokenizationContext("df", Collections.singletonList(toke));
        List<Token> results = context.tokenize();
        assertEquals("1(1d3 2 -)", ExpressionUtils.toString(results));
        context = new TokenizationContext("3df", Arrays.asList(toke, new IntegerTokenizer()));
        results = context.tokenize();
        assertEquals("3(1d3 2 -)", ExpressionUtils.toString(results));
    }

    @Test
    public void testShouldThrowCorrectErrorWhenNoDieType() throws Exception {
        StringScanner s = new StringScanner("d");
        TokenizationContext con = new TokenizationContext(s, new ArrayList<Tokenizer>());
        try {
            toke.tryTokenize(con, s);
            fail();
        } catch (InvalidExpressionException e) {
            // expected
        }
    }

    @Test
    public void testTryTokenize() throws Exception {
        List<Tokenizer> tokenizerList = new ArrayList<>();
        TokenizationContext context = new TokenizationContext(new StringScanner(""), tokenizerList);
        StringScanner steve = new StringScanner("d20xxxxxx");
        toke.tryTokenize(context, steve);
        assertEquals("1d20", context.getLastToken().toString());
        assertEquals(3, steve.getCursor());
    }

    @Test
    public void testTryTokenizeWithMultiplier() throws Exception {
        List<Tokenizer> tokenizerList = new ArrayList<>();
        TokenizationContext context = new TokenizationContext(new StringScanner(""), tokenizerList);
        context.pushToOutput(new IntegerToken(10));
        StringScanner steve = new StringScanner("d20xxxxxx");
        toke.tryTokenize(context, steve);
        assertEquals("10d20", context.getLastToken().toString());
        assertEquals(3, steve.getCursor());
    }

    @Test
    public void testTryTokenizeWithNegativeMultiplier() throws Exception {
        List<Tokenizer> tokenizerList = new ArrayList<>();
        TokenizationContext context = new TokenizationContext(new StringScanner(""), tokenizerList);
        context.pushToOutput(new IntegerToken(-10));
        StringScanner steve = new StringScanner("d20xxxxxx");
        toke.tryTokenize(context, steve);
        assertEquals("-10d20", context.getLastToken().toString());
        assertEquals(3, steve.getCursor());
    }
}