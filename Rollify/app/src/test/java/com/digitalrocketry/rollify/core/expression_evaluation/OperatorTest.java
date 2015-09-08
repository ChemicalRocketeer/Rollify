package com.digitalrocketry.rollify.core.expression_evaluation;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

import static com.digitalrocketry.rollify.core.expression_evaluation.Operator.*;

/**
 * Created by David Aaron Suddjian on 9/7/2015.
 */
public class OperatorTest {

    @Test
    public void testIsDefined() throws Exception {
        assertTrue(Operator.isDefined("+"));
        assertTrue(Operator.isDefined("-"));
        assertTrue(Operator.isDefined("*"));
        assertTrue(Operator.isDefined("/"));
        assertTrue(Operator.isDefined('+'));
        assertTrue(Operator.isDefined('-'));
        assertTrue(Operator.isDefined('*'));
        assertTrue(Operator.isDefined('/'));
        assertFalse(Operator.isDefined("invalid"));
        assertFalse(Operator.isDefined('v'));
    }

    @Test
    public void testGet() throws Exception {
        assertEquals(ADD, get("+"));
        assertEquals(SUB, get("-"));
        assertEquals(MUL, get("*"));
        assertEquals(DIV, get("/"));
        assertEquals(ADD, get('+'));
        assertEquals(SUB, get('-'));
        assertEquals(MUL, get('*'));
        assertEquals(DIV, get('/'));
        assertEquals(null, get("v"));
        assertEquals(null, get('v'));
    }

    @Test
    public void testGetPrecedence() throws Exception {
        assertEquals(1f, ADD.getPrecedence(), 0f);
        assertEquals(1f, SUB.getPrecedence(), 0f);
        assertEquals(2f, MUL.getPrecedence(), 0f);
        assertEquals(2f, DIV.getPrecedence(), 0f);
    }

    @Test
    public void testGetSymbol() throws Exception {
        assertEquals("+", ADD.getSymbol());
        assertEquals("-", SUB.getSymbol());
        assertEquals("*", MUL.getSymbol());
        assertEquals("/", DIV.getSymbol());
    }

    @Test
    public void testOperate() throws Exception {
        Stack<Long> stack = new Stack<>();

        stack.push((long) 5);
        stack.push((long) 5);
        ADD.operate(stack);
        assertEquals(1, stack.size());
        assertEquals(10, (long) stack.pop());

        stack.push((long) 4);
        stack.push((long) 3);
        SUB.operate(stack);
        assertEquals(1, stack.size());
        assertEquals(1, (long) stack.pop());

        stack.push((long) 4);
        stack.push((long) 4);
        MUL.operate(stack);
        assertEquals(1, stack.size());
        assertEquals(16, (long) stack.pop());

        stack.push((long) 6);
        stack.push((long) 2);
        DIV.operate(stack);
        assertEquals(1, stack.size());
        assertEquals(3, (long) stack.pop());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("+", ADD.toString());
        assertEquals("-", SUB.toString());
        assertEquals("*", MUL.toString());
        assertEquals("/", DIV.toString());
    }
}