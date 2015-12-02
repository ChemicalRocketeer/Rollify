package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class IntegerTokenTest {

    @Test
    public void testOperate() throws Exception {
        IntegerToken toke = new IntegerToken(5);
        Stack<Long> stack = new Stack<>();
        toke.operate(stack);
        assertEquals(5, (long) stack.peek());
        toke = new IntegerToken(8);
        toke.operate(stack);
        assertEquals(2, stack.size());
        assertEquals(8, (long) stack.peek());
    }

    @Test
    public void testIsNumber() throws Exception {
        IntegerToken toke = new IntegerToken(0);
        assertTrue(toke.isNumber());
    }

    @Test
    public void testToString() throws Exception {
        IntegerToken toke = new IntegerToken(0);
        assertEquals("0", toke.toString());
        toke = new IntegerToken(-500);
        assertEquals("-500", toke.toString());
        toke = new IntegerToken(Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), toke.toString());
    }
}