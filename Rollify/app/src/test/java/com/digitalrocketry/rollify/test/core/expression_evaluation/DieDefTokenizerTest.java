package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.DieDefTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;

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
        context.pushToOutput(new IntegerToken(10));
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
        context.pushToOutput(new IntegerToken(-10));
        StringScanner steve = new StringScanner("d20xxxxxx");
        toke.tryTokenize(context, steve);
        assertEquals("-10d20", context.getLastToken().toString());
        assertEquals(3, steve.getCursor());
    }
}