package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.DieDefTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.NumberToken;
import com.digitalrocketry.rollify.core.expression_evaluation.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.Tokenizer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/9/2015.
 */
public class DieDefTokenizerTest {

    @Test
    public void testTryTokenize() throws Exception {
        DieDefTokenizer toke = new DieDefTokenizer();
        List<Tokenizer> tokenizerList = new ArrayList<>();
        TokenizationContext context = new TokenizationContext(new StringScanner(""), tokenizerList);
        StringScanner steve = new StringScanner("d20xxxxxx");
        toke.tryTokenize(context, steve);
        assertEquals("1d20", context.getLastToken().toString());
        assertEquals(3, steve.getCursor());
    }

    @Test
    public void testTryTokenizeWithMultiplier() throws Exception {
        DieDefTokenizer toke = new DieDefTokenizer();
        List<Tokenizer> tokenizerList = new ArrayList<>();
        TokenizationContext context = new TokenizationContext(new StringScanner(""), tokenizerList);
        context.commitToken(new NumberToken(10));
        StringScanner steve = new StringScanner("d20xxxxxx");
        toke.tryTokenize(context, steve);
        assertEquals("10d20", context.getLastToken().toString());
        assertEquals(3, steve.getCursor());
    }

    @Test
    public void testTryTokenizeWithNegativeMultiplier() throws Exception {
        DieDefTokenizer toke = new DieDefTokenizer();
        List<Tokenizer> tokenizerList = new ArrayList<>();
        TokenizationContext context = new TokenizationContext(new StringScanner(""), tokenizerList);
        context.commitToken(new NumberToken(-10));
        StringScanner steve = new StringScanner("d20xxxxxx");
        toke.tryTokenize(context, steve);
        assertEquals("-10d20", context.getLastToken().toString());
        assertEquals(3, steve.getCursor());
    }
}