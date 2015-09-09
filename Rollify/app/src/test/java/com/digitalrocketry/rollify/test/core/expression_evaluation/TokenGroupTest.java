package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.DieToken;
import com.digitalrocketry.rollify.core.expression_evaluation.NumberToken;
import com.digitalrocketry.rollify.core.expression_evaluation.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.TokenGroup;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class TokenGroupTest {

    @Test
    public void testOperate() throws Exception {
        Stack<Long> stack = new Stack<>();
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(2));
        tokens.add(new NumberToken(2));
        tokens.add(Operator.ADD);
        TokenGroup toke = new TokenGroup(new NumberToken(1), tokens);
        toke.operate(stack);
        assertEquals(1, stack.size());
        assertEquals(4, (long) stack.peek());
        toke.operate(stack);
        assertEquals(2, stack.size());
        assertEquals(4, (long) stack.peek());

        tokens.clear();
        tokens.add(new NumberToken(1));
        toke = new TokenGroup(new NumberToken(5), tokens);
        toke.operate(stack);
        assertEquals(3, stack.size());
        assertEquals(5, (long) stack.peek());
    }

    @Test
    public void testIsNumber() throws Exception {
        assertTrue(new TokenGroup(new NumberToken(1), new ArrayList<Token>()).isNumber());
    }

    @Test
    public void testToString() throws Exception {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(2));
        tokens.add(new NumberToken(2));
        tokens.add(Operator.ADD);
        TokenGroup toke = new TokenGroup(new NumberToken(1), tokens);
        assertEquals("1(2 2 +)", toke.toString());
        List<Token> tokens2 = new ArrayList<>();
        tokens2.add(new NumberToken(20));
        tokens2.add(new NumberToken(4));
        tokens2.add(Operator.MUL);
        TokenGroup toke2 = new TokenGroup(new NumberToken(2), tokens2);
        assertEquals("2(20 4 *)", toke2.toString());
        List<Token> tokens3 = new ArrayList<>();
        tokens3.add(toke);
        tokens3.add(toke2);
        TokenGroup toke3 = new TokenGroup(new NumberToken(3), tokens3);
        assertEquals("3(1(2 2 +) 2(20 4 *))", toke3.toString());
    }
}