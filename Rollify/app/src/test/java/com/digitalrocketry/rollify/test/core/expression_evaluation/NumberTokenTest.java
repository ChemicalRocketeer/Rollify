package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.NumberToken;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class NumberTokenTest {

    @Test
    public void testOperate() throws Exception {
        NumberToken toke = new NumberToken(5);
        Stack<Long> stack = new Stack<>();
        toke.operate(stack);
        assertEquals(5, (long) stack.peek());
        toke = new NumberToken(8);
        toke.operate(stack);
        assertEquals(2, stack.size());
        assertEquals(8, (long) stack.peek());
    }

    @Test
    public void testIsNumber() throws Exception {
        NumberToken toke = new NumberToken(0);
        assertTrue(toke.isNumber());
    }

    @Test
    public void testToString() throws Exception {
        NumberToken toke = new NumberToken(0);
        assertEquals("0", toke.toString());
        toke = new NumberToken(-500);
        assertEquals("-500", toke.toString());
        toke = new NumberToken(Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), toke.toString());
    }
}