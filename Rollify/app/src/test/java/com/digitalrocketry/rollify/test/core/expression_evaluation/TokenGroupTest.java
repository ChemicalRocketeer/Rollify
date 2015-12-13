package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.Result;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.ParenTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class TokenGroupTest {

    @Test
    public void testEmptyParensShouldEvaluateToZero() throws Exception {
        TokenizationContext context = new TokenizationContext("()", Collections.singletonList(new ParenTokenizer()));
        Result r = new Evaluator().evaluate(context.tokenize());
        assertEquals(0, r.getResult());
    }

    @Test
    public void testOperate() throws Exception {
        Stack<Long> stack = new Stack<>();
        List<Token> tokens = new ArrayList<>();
        tokens.add(new IntegerToken(2));
        tokens.add(new IntegerToken(2));
        tokens.add(Operator.ADD);
        TokenGroup toke = new TokenGroup(tokens);
        toke.operate(stack);
        assertEquals(1, stack.size());
        assertEquals(4, (long) stack.peek());
        toke.operate(stack);
        assertEquals(2, stack.size());
        assertEquals(4, (long) stack.peek());
    }

    @Test
    public void testIsNumber() throws Exception {
        assertTrue(new TokenGroup(new ArrayList<Token>()).isNumber());
    }

    @Test
    public void testToString() throws Exception {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new IntegerToken(2));
        tokens.add(new IntegerToken(2));
        tokens.add(Operator.ADD);
        TokenGroup toke = new TokenGroup(tokens);
        assertEquals("(2 2 +)", toke.toString());
        List<Token> tokens2 = new ArrayList<>();
        tokens2.add(new IntegerToken(20));
        tokens2.add(new IntegerToken(4));
        tokens2.add(Operator.MUL);
        TokenGroup toke2 = new TokenGroup(tokens2);
        assertEquals("(20 4 *)", toke2.toString());
        List<Token> tokens3 = new ArrayList<>();
        tokens3.add(toke);
        tokens3.add(toke2);
        tokens3.add(Operator.ADD);
        TokenGroup toke3 = new TokenGroup(tokens3);
        assertEquals("((2 2 +) (20 4 *) +)", toke3.toString());
    }
}