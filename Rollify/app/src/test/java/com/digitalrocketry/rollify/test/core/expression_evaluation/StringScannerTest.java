package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class StringScannerTest {

    @Test
    public void testReadUntil() throws Exception {
        StringScanner s = new StringScanner("testxtest");
        assertEquals("test", s.readUntil('x'));
        assertTrue(s.hasNext());
        assertTrue(s.next() == 't');
        assertEquals(null, s.readUntil('x'));
        assertFalse(s.hasNext());
    }

    @Test
    public void testHasNext() throws Exception {
        StringScanner s = new StringScanner("test\n");
        assertTrue(s.hasNext());
        s = new StringScanner("k");
        assertTrue(s.hasNext());
        s = new StringScanner("test", 3);
        assertTrue(s.hasNext());
        s = new StringScanner("");
        assertFalse(s.hasNext());
        s = new StringScanner("test", 4);
        assertFalse(s.hasNext());
        s = new StringScanner("test", 10);
        assertFalse(s.hasNext());
    }

    @Test
    public void testNext() throws Exception {
        StringScanner s = new StringScanner("hello");
        assertEquals('h', s.next());
        assertEquals('e', s.next());
        assertEquals('l', s.next());
        assertEquals('l', s.next());
        assertEquals('o', s.next());
        assertFalse(s.hasNext());
    }

    @Test
    public void testPeek() throws Exception {
        StringScanner s = new StringScanner("foo");
        assertEquals('f', s.peek());
        assertEquals('f', s.peek());
        s = new StringScanner("bar", 2);
        assertEquals('r', s.peek());
    }

    @Test
    public void testNextBigInteger() throws Exception {
        BigInteger expected = new BigInteger("123456789012345678901234567890");
        StringScanner s = new StringScanner(expected.toString());
        BigInteger actual = s.nextBigInteger();
        assertEquals(expected, actual);
    }

    @Test
    public void testNextLong() throws Exception {
        StringScanner s = new StringScanner("2048");
        assertEquals(2048, s.nextLong());
        s = new StringScanner(String.valueOf(Long.MAX_VALUE));
        assertEquals(Long.MAX_VALUE, s.nextLong());
        s = new StringScanner("0");
        assertEquals(0, s.nextLong());
    }

    @Test
    public void testSkipWhitespace() throws Exception {
        StringScanner s = new StringScanner(" x");
        s.skipWhitespace();
        assertEquals('x', s.next());
        s = new StringScanner("      ");
        s.skipWhitespace();
        assertFalse(s.hasNext());
        s = new StringScanner("steve");
        s.skipWhitespace();
        assertEquals('s', s.next());
        s = new StringScanner("");
        s.skipWhitespace();
        assertFalse(s.hasNext());
    }

    @Test
    public void testDuplicateConstructor() throws Exception {
        StringScanner s1 = new StringScanner("string");
        assertEquals('s', s1.next());
        StringScanner s2 = new StringScanner(s1);
        assertEquals('t', s2.next());
        assertEquals('t', s1.next());
    }
}