package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.OperatorTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/9/2015.
 */
public class OperatorTokenizerTest {

    @Test
    public void testTryTokenizeAdd() throws Exception {
        StringScanner scanner = new StringScanner("+xxx");
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        context.pushToOutput(new IntegerToken(1));
        new OperatorTokenizer().tryTokenize(context, scanner);
        assertEquals("+", context.getLastToken().toString());
        assertEquals(1, scanner.getCursor());
    }

    @Test
    public void testTryTokenizeSub() throws Exception {
        StringScanner scanner = new StringScanner("-  xxx");
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        context.pushToOutput(new IntegerToken(1));
        new OperatorTokenizer().tryTokenize(context, scanner);
        assertEquals("-", context.getLastToken().toString());
        assertEquals(1, scanner.getCursor());
    }

    @Test
    public void testTryTokenizeMul() throws Exception {
        StringScanner scanner = new StringScanner("*xxx");
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        context.pushToOutput(new IntegerToken(1));
        new OperatorTokenizer().tryTokenize(context, scanner);
        assertEquals("*", context.getLastToken().toString());
        assertEquals(1, scanner.getCursor());
    }

    @Test
    public void testTryTokenizeDiv() throws Exception {
        StringScanner scanner = new StringScanner("/xxx");
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        context.pushToOutput(new IntegerToken(1));
        new OperatorTokenizer().tryTokenize(context, scanner);
        assertEquals("/", context.getLastToken().toString());
        assertEquals(1, scanner.getCursor());
    }

    @Test
    public void testTryTokenizeNegativeNumber() throws Exception {
        StringScanner scanner = new StringScanner("-1234xxx");
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        new OperatorTokenizer().tryTokenize(context, scanner);
        assertEquals("-1234", context.getLastToken().toString());
        assertEquals(5, scanner.getCursor());
    }
}