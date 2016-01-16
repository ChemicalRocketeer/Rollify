package com.digitalrocketry.rollify;

import com.digitalrocketry.rollify.utils.Range;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 1/13/2016.
 */
public class CalculatorDisplayFragmentTest {

    @Test
    public void testSmartBackspaceRangeWithNoFancyStuff() throws Exception {
        String testString = "aaaaaaaaaaaaa";
        int cursor = 5;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(4, result.min);
        assertEquals(4, result.max);
    }

    @Test
    public void testSmartBackspaceRangeAtBeginningOfString() throws Exception {
        String testString = "aaaaaaaaaaaaa";
        int cursor = 0;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNull(result);
    }

    @Test
    public void testSmartBackspaceRangeWithEmptyStringShouldBeNull() throws Exception {
        String testString = "";
        int cursor = 0;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNull(result);
    }

    @Test
    public void testSmartBackspaceRangeAfterEndOfStringShouldBeNull() throws Exception {
        String testString = "aaa";
        int cursor = 5;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNull(result);
    }

    @Test
    public void testSmartBackspaceRangeAtSecondCharacterOfString() throws Exception {
        String testString = "aaaaaaaaaaaaa";
        int cursor = 1;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(0, result.min);
        assertEquals(0, result.max);
    }

    @Test
    public void testSmartBackspaceRangeAtEndOfString() throws Exception {
        String testString = "aaaaaaaaaaaaa";
        int cursor = testString.length();
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(testString.length() - 1, result.min);
        assertEquals(testString.length() - 1, result.max);
    }

    @Test
    public void findSmartBackspaceRangeJustBeforeNumberShouldNotSelectNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 3;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(2, result.min);
        assertEquals(2, result.max);
    }

    @Test
    public void testFindSmartBackspaceRangeAtBeginningOfNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 4;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(12, result.max);
    }

    @Test
    public void testSmartBackspaceRangeAtEndOfNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 13;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(12, result.max);
    }

    @Test
    public void testSmartBackspaceRangeBeforeEndOfNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 12;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(12, result.max);
    }

    @Test
    public void testSmartBackspaceRangeAfterEndOfNumber() throws Exception {
        String testString = "aaa1234567890aaa";
        int cursor = 14;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(13, result.min);
        assertEquals(13, result.max);
    }

    @Test
    public void testSmartBackspaceRangeBeforeBracket() throws Exception {
        String testString = "aaa[xxxxx]aaa";
        int cursor = 3;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(2, result.min);
        assertEquals(2, result.max);
    }

    @Test
    public void testSmartBackspaceRangeAtEndOfBracket() throws Exception {
        String testString = "aaa[xxxxx]aaa";
        int cursor = 10;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(9, result.max);
    }

    @Test
    public void testSmartBackspaceRangeBeforeEndOfBracket() throws Exception {
        String testString = "aaa[xxxxx]aaa";
        int cursor = 9;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(9, result.max);
    }

    @Test
    public void testSmartBackspaceRangeInsideBrackets() throws Exception {
        String testString = "aaa[xxxxx]aaa";
        int cursor = 7;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(9, result.max);
    }

    @Test
    public void testSmartBackspaceRangeAtBeginningOfBracket() throws Exception {
        String testString = "aaa[xxxxx]aaa";
        int cursor = 4;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, null);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(9, result.max);
    }

    @Test
    public void testSmartBackspaceRangeWithPreviouslyDeletedBracket() throws Exception {
        String testString = "aaa[xxxxx";
        Stack<String> stack = new Stack<>();
        stack.push("]");
        int cursor = 9;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, stack);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(8, result.max);
    }

    @Test
    public void testSmartBackspaceRangeWithPreviousBracketWithoutFullBrackets() throws Exception {
        String testString = "aaaxxxxx";
        Stack<String> stack = new Stack<>();
        stack.push("]");
        int cursor = 8;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, stack);
        assertNotNull(result);
        assertEquals(7, result.min);
        assertEquals(7, result.max);
    }

    @Test
    public void testSmartBackspaceRangeWithPreviousBracketAndOtherBrackets() throws Exception {
        String testString = "aaa[xxx]xx";
        Stack<String> stack = new Stack<>();
        stack.push("]");
        int cursor = 10;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, stack);
        assertNotNull(result);
        assertEquals(9, result.min);
        assertEquals(9, result.max);
    }

    @Test
    public void testSmartBackspaceRangeWithPreviousBracketAndOtherText() throws Exception {
        String testString = "aaa[xxxxx";
        Stack<String> stack = new Stack<>();
        stack.push("aa]aa");
        int cursor = 9;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, stack);
        assertNotNull(result);
        assertEquals(3, result.min);
        assertEquals(8, result.max);
    }

    @Test
    public void testSmartBackspaceRangeWithPreviousFullBrackets() throws Exception {
        String testString = "aaa[xxxxx";
        Stack<String> stack = new Stack<>();
        stack.push("[bbb]");
        int cursor = 9;
        Range result = CalculatorDisplayFragment.smartBackspaceRange(testString, cursor, stack);
        assertNotNull(result);
        assertEquals(8, result.min);
        assertEquals(8, result.max);
    }
}