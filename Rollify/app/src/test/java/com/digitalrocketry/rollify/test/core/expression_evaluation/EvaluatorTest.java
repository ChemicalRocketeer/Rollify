package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.*;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.DieToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.NumberToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class EvaluatorTest {

    private RandomProvider originalRandomProvider;

    @Before
    public void setUp() throws Exception {
        originalRandomProvider = ExpressionUtils.RAND;
    }

    @After
    public void tearDown() throws Exception {
        ExpressionUtils.RAND = originalRandomProvider;
    }

    @Test
    public void testEvaluate() throws Exception {
        ExpressionUtils.RAND = TestingUtils.MAX;
        Evaluator eva = new Evaluator();
        Result r = eva.evaluate(" 2 * 2d6 + d12 ( 7d8-2) * -3   -9");
        assertEquals(-1929, r.getResult());
    }

    @Test
    public void testEvaluate1() throws Exception {
        List<Tokenizer> tokenizerList = new ArrayList<>();
        tokenizerList.add(TestingUtils.ONE_OR_ADD);
        TokenizationContext context = new TokenizationContext(new StringScanner("000"), tokenizerList);
        Result r = new Evaluator().evaluate(context);
        assertEquals(2, r.getResult());
    }

    @Test
    public void testEvaluate2() throws Exception {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(1));
        tokens.add(new NumberToken(1));
        tokens.add(Operator.SUB);
        Result r = new Evaluator().evaluate(tokens);
        assertEquals(0, r.getResult());
    }

    @Test
    public void testEvaluateToken() throws Exception {
        ExpressionUtils.RAND = TestingUtils.MAX;
        Result r = new Evaluator().evaluate(new DieToken(new NumberToken(2), 20));
        assertEquals(40, r.getResult());
    }
}