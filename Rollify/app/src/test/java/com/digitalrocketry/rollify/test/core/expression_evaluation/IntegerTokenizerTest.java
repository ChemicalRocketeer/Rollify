package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.IntegerTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/9/2015.
 */
public class IntegerTokenizerTest {

    @Test
    public void testTryTokenize() throws Exception {
        StringScanner steve = new StringScanner("42xxxxx");
        TokenizationContext context = new TokenizationContext(new StringScanner(""), new ArrayList<Tokenizer>());
        new IntegerTokenizer().tryTokenize(context, steve);
        assertEquals("42", context.getLastToken().toString());
        assertEquals(2, steve.getCursor());
    }
}