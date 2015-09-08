package com.digitalrocketry.rollify.core.expression_evaluation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class DieTokenTest {

    public static final RandomProvider MIN = new RandomProvider() {
        @Override
        public long nextLong(long min, long max) {
            return min;
        }
    };

    public static final RandomProvider MAX = new RandomProvider() {
        @Override
        public long nextLong(long min, long max) {
            return max;
        }
    };

    private RandomProvider originalRandomProvider;

    @Before
    public void setUp() throws Exception {
        originalRandomProvider = Utils.RAND;
    }

    @After
    public void tearDown() throws Exception {
        Utils.RAND = originalRandomProvider;
    }

    @Test
    public void testIsNumber() throws Exception {
        DieToken toke = new DieToken(new NumberToken(1), 6);
        assertTrue(toke.isNumber());
    }

    @Test
    public void testOperate() throws Exception {
        Utils.RAND = MIN;
        DieToken toke = new DieToken(new NumberToken(6), 6);
        Stack<Long> stack = new Stack<>();
        toke.operate(stack);
        assertEquals(6, (long) stack.peek());
        assertEquals(1, stack.size());

        Utils.RAND = MAX;
        toke = new DieToken(new NumberToken(2), 6);
        stack = new Stack<>();
        toke.operate(stack);
        assertEquals(12, (long) stack.peek());
        assertEquals(1, stack.size());

        toke = new DieToken(new DieToken(new NumberToken(1), 10), 10);
        stack = new Stack<>();
        toke.operate(stack);
        assertEquals(100, (long) stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testToString() throws Exception {
        DieToken toke = new DieToken(new NumberToken(5), 20);
        assertEquals("5d20", toke.toString());
    }
}