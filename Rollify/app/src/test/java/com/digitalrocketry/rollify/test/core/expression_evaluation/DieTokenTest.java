package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokens.DieToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
import com.digitalrocketry.rollify.core.expression_evaluation.RandomProvider;
import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class DieTokenTest {

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
    public void testIsNumber() throws Exception {
        DieToken toke = new DieToken(6);
        assertTrue(toke.isNumber());
    }

    @Test
    public void testOperate() throws Exception {
        ExpressionUtils.RAND = TestingUtils.MIN;
        DieToken toke = new DieToken(6);
        Stack<Long> stack = new Stack<>();
        toke.operate(stack);
        assertEquals(1, (long) stack.peek());
        assertEquals(1, stack.size());

        ExpressionUtils.RAND = TestingUtils.MAX;
        toke = new DieToken(6);
        stack = new Stack<>();
        toke.operate(stack);
        assertEquals(6, (long) stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testToString() throws Exception {
        DieToken toke = new DieToken(20);
        assertEquals("d20", toke.toString());
    }
}