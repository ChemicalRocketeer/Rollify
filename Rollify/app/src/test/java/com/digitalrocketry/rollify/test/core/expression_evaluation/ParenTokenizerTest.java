package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.ParenTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/9/2015.
*/
public class ParenTokenizerTest {

    @Test
    public void testReusability() throws Exception {
        Tokenizer toke = new ParenTokenizer();
        StringScanner scanner = new StringScanner("((((()");
        TokenizationContext context = new TokenizationContext(scanner, Collections.singletonList(toke));
        context.tokenize();
        scanner = new StringScanner("(())");
        context = new TokenizationContext(scanner, Collections.singletonList(toke));
        try {
            new Evaluator().evaluate(context.tokenize());
        } catch (InvalidExpressionException e) {
            fail();
        }
    }

    @Test
    public void testMismtchedOpenParens() throws Exception {
        Tokenizer toke = new ParenTokenizer();
        TokenizationContext context = new TokenizationContext("(((()", Collections.singletonList(toke));
        try {
            new Evaluator().evaluate(context.tokenize());
            fail("ParenTokenizer not failing on mismatched open parens");
        } catch(InvalidExpressionException e) {
            // expected behavior
        }
    }

    @Test
    public void testMismtchedCloseParens() throws Exception {
        Tokenizer toke = new ParenTokenizer();
        TokenizationContext context = new TokenizationContext("()))))", Collections.singletonList(toke));
        try {
            new Evaluator().evaluate(context.tokenize());
            fail("ParenTokenizer not failing on mismatched close parens");
        } catch(InvalidExpressionException e) {
            // expected behavior
        }
    }

    @Test
    public void testTryTokenizeWithoutMultiplier() throws Exception {
        StringScanner steve = new StringScanner("()");
        Tokenizer toke = new ParenTokenizer();
        TokenizationContext context = new TokenizationContext(steve, Arrays.asList(toke));
        toke.tryTokenize(context, steve);
        assertEquals("(", context.getLastToken().toString());
        assertEquals(1, steve.getCursor());
        toke.tryTokenize(context, steve);
        assertEquals("1()", context.getLastToken().toString());
        assertEquals(2, steve.getCursor());
    }

    @Test
    public void testTryTokenizeWithMultiplier() throws Exception {
        StringScanner steve = new StringScanner("()");
        Tokenizer toke = new ParenTokenizer();
        TokenizationContext context = new TokenizationContext(steve, Arrays.asList(toke));
        context.pushToOutput(new IntegerToken(200));
        toke.tryTokenize(context, steve);
        assertEquals("(", context.getLastToken().toString());
        assertEquals(1, steve.getCursor());
        toke.tryTokenize(context, steve);
        assertEquals("200()", context.getLastToken().toString());
        assertEquals(2, steve.getCursor());
    }

    @Test
    public void testTryTokenizeWithNesting() throws Exception {
        StringScanner steve = new StringScanner("(())");
        Tokenizer toke = new ParenTokenizer();
        TokenizationContext context = new TokenizationContext(steve, Arrays.asList(toke));
        context.pushToOutput(new IntegerToken(2));
        toke.tryTokenize(context, steve);
        assertEquals("(", context.getLastToken().toString());
        assertEquals(1, steve.getCursor());
        toke.tryTokenize(context, steve);
        assertEquals("(", context.getLastToken().toString());
        assertEquals(2, steve.getCursor());
        toke.tryTokenize(context, steve);
        assertEquals("1()", context.getLastToken().toString());
        assertEquals(3, steve.getCursor());
        toke.tryTokenize(context, steve);
        assertEquals("2(1())", context.getLastToken().toString());
        assertEquals(4, steve.getCursor());
    }
}