package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/9/2015.
 */
public class TokenizationContextTest {

    @Test
    public void testTokenize() throws Exception {
        List<Tokenizer> tokenizers = Arrays.asList(TestingUtils.ONE_OR_ADD);
        StringScanner steve = new StringScanner("12345");
        TokenizationContext context = new TokenizationContext(steve, tokenizers);
        context.tokenize();
        assertFalse(steve.hasNext());
        List<Token> output = context.getOutputTokens();
        Stack<Operator> opStack = context.getOpStack();
        assertEquals(5, output.size());
        assertEquals("1", output.get(0).toString());
        assertEquals("1", output.get(1).toString());
        assertEquals("+", output.get(2).toString());
        assertEquals("1", output.get(3).toString());
        assertEquals("+", output.get(4).toString());
        assertEquals(0, opStack.size());
    }

    @Test
    public void testFinish() throws Exception {
        Tokenizer finisher = new Tokenizer() {
            @Override
            public boolean tryTokenize(TokenizationContext context, StringScanner tempScanner) {
                tempScanner.next();
                context.finish();
                return true;
            }
        };
        StringScanner steve = new StringScanner("test");
        TokenizationContext context = new TokenizationContext(steve, Arrays.asList(finisher));
        context.tokenize();
        assertEquals(1, steve.getCursor());
        assertEquals(0, context.getOpStack().size());
        assertEquals(0, context.getOutputTokens().size());
    }

    @Test
    public void testGetLastToken() throws Exception {
        List<Tokenizer> tokenizers = Arrays.asList(TestingUtils.ONE_OR_ADD);
        StringScanner steve = new StringScanner("12345");
        TokenizationContext context = new TokenizationContext(steve, tokenizers);
        assertEquals(null, context.getLastToken());
        context.tokenize();
        assertNotEquals(null, context.getLastToken());
    }

    @Test
    public void testLastTokenWasnumber() throws Exception {
        List<Tokenizer> tokenizers = Arrays.asList(TestingUtils.ONE_OR_ADD);
        StringScanner steve = new StringScanner("1");
        TokenizationContext context = new TokenizationContext(steve, tokenizers);
        context.tokenize();
        assertTrue(context.lastTokenWasnumber());
        steve = new StringScanner("");
        context = new TokenizationContext(steve, tokenizers);
        context.tokenize();
        assertFalse(context.lastTokenWasnumber());
    }
}