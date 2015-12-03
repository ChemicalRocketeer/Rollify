package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.FormulaTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;
import com.digitalrocketry.rollify.db.Formula;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 12/2/2015.
 */
public class FormulaTokenizerTest {

    private static Formula testFormula, nestFormula, loopFormula1, loopFormula2;
    private FormulaTokenizer formulatizer;

    @BeforeClass
    public static void setup() {
        testFormula = new Formula("test formula", "1+1");
        nestFormula = new Formula("nest formula", "1+[test formula]");
        loopFormula1 = new Formula("loop formula 1", "1+[loop formula 2]");
        loopFormula2 = new Formula("loop formula 2", "1+[loop formula 1]");
        FormulaTokenizer.setFormulaProvider(new FormulaTokenizer.FormulaProvider() {
            @Override
            public Formula getFormula(String name) {
                switch (name) {
                    case "test formula":
                        return testFormula;
                    case "nest formula":
                        return nestFormula;
                    case "loop formula 1":
                        return loopFormula1;
                    case "loop formula 2":
                        return loopFormula2;
                    default:
                        return null;
                }
            }
        });
    }

    @Before
    public void before() {
        formulatizer = new FormulaTokenizer();
    }

    @Test
    public void testShouldReadFormulas() throws Exception {
        StringScanner s = new StringScanner("[test formula]");
        TokenizationContext context = new TokenizationContext(s, Arrays.asList(formulatizer, TestingUtils.ONE_OR_ADD));
        List<Token> results = context.tokenize();
        assertFalse(s.hasNext());
        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof TokenGroup);
        assertEquals("1(1 1 +)", results.get(0).toString());
    }

    @Test
    public void testShouldThrowOnMissingCloseBrackets() throws Exception {
        StringScanner s = new StringScanner("[test formula");
        TokenizationContext context = new TokenizationContext(s, Collections.singletonList((Tokenizer) formulatizer));
        try {
            context.tokenize();
            fail();
        } catch (InvalidExpressionException e) {
            assertTrue(e.getMessage().contains(FormulaTokenizer.MISMATCHED_BRACKETS_MSG));
        }
    }

    @Test
    public void testShouldThrowOnSelfReferentialFormula() throws Exception {
        StringScanner s = new StringScanner("[loop formula 1]");
        TokenizationContext context = new TokenizationContext(s, Arrays.asList(formulatizer, TestingUtils.ONE_OR_ADD));
        try {
            context.tokenize();
            fail();
        } catch (InvalidExpressionException e) {
            assertTrue(e.getMessage().contains(FormulaTokenizer.SELF_REFERENTIAL_FORMULA_MSG));
        }
    }
}