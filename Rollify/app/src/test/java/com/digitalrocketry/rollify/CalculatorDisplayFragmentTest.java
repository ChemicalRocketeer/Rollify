package com.digitalrocketry.rollify;

import com.digitalrocketry.rollify.utils.Range;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 1/13/2016.
 */
public class CalculatorDisplayFragmentTest {

    @Test
    public void testBasicFindSmartBackspaceRangeWithNoFancyStuff() throws Exception {
        String testString = "aaaaaaaaaaaaa";
        int cursor = 5;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(4, result.min);
        assertEquals(4, result.max);
    }

    @Test
    public void testBasicFindSmartBackspaceRangeAtBeginningOfString() throws Exception {
        String testString = "aaaaaaaaaaaaa";
        int cursor = 0;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNull(result);
    }

    @Test
    public void testBasicFindSmartBackspaceRangeAtSecondCharacterOfString() throws Exception {
        String testString = "aaaaaaaaaaaaa";
        int cursor = 1;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(0, result.min);
        assertEquals(0, result.max);
    }

    @Test
    public void testBasicFindSmartBackspaceRangeAtEndOfString() throws Exception {
        String testString = "aaaaaaaaaaaaa";
        int cursor = testString.length();
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(testString.length() - 1, result.min);
        assertEquals(testString.length() - 1, result.max);
    }

    @Test
    public void findSmartBackspaceRangeJustBeforeNumberShouldNotSelectNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 3;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(2, result.min);
        assertEquals(2, result.max);
    }

    @Test
    public void testFindSmartBackspaceRangeAtBeginningOfNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 4;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(12, result.max);
    }

    @Test
    public void testBasicFindSmartBackspaceRangeAtEndOfNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 13;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(12, result.max);
    }

    @Test
    public void testBasicFindSmartBackspaceRangeBeforeEndOfNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 12;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(12, result.max);
    }

    @Test
    public void testBasicFindSmartBackspaceRangeAfterEndOfNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 14;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(13, result.min);
        assertEquals(13, result.max);
    }

    @Test
    public void testBasicFindSmartBackspaceRangeBeforeBracket() throws Exception {
        String testString = "aaa[xxxxx]aaa";
        int cursor = 13;
        Range result = CalculatorDisplayFragment.findSmartBackspaceRange(testString, cursor);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(12, result.max);
    }
}