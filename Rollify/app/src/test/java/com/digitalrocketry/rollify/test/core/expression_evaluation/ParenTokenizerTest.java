package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokens.NumberToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.ParenTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/9/2015.
 */
public class ParenTokenizerTest {

    @Test
    public void testTryTokenizeWithoutMultiplier() throws Exception {
        StringScanner steve = new StringScanner("()aaaaaaaaa");
        Tokenizer toke = new ParenTokenizer();
        TokenizationContext context = new TokenizationContext(steve, Arrays.asList(toke));
        toke.tryTokenize(context, steve);
        assertEquals("1()", context.getLastToken().toString());
        assertEquals(2, steve.getCursor());
    }

    @Test
    public void testTryTokenizeWithMultiplier() throws Exception {
        StringScanner steve = new StringScanner("()aaaaaaaaa");
        Tokenizer toke = new ParenTokenizer();
        TokenizationContext context = new TokenizationContext(steve, Arrays.asList(toke));
        context.commitToken(new NumberToken(200));
        toke.tryTokenize(context, steve);
        assertEquals("200()", context.getLastToken().toString());
        assertEquals(2, steve.getCursor());
    }

    @Test
    public void testTryTokenizeWithNesting() throws Exception {
        StringScanner steve = new StringScanner("(())aaaaaaaaa");
        Tokenizer toke = new ParenTokenizer();
        TokenizationContext context = new TokenizationContext(steve, Arrays.asList(toke));
        context.commitToken(new NumberToken(2));
        toke.tryTokenize(context, steve);
        assertEquals("2(1())", context.getLastToken().toString());
        assertEquals(4, steve.getCursor());
    }
}