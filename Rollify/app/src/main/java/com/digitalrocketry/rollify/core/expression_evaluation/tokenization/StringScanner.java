package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.math.BigInteger;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 *
 * A simple class specialized to provide methods to read through a string looking for tokens. It can find
 * several types of data. It uses a cursor to point at a character of the string. The cursor advances as
 * it reads data.
 *
 * To peek ahead more than one character, it is recommended to create a new StringScanner
 * using the original as a parameter, and read whatever you want. This will leave the cursor of the original
 * scanner unaltered.
 */
public class StringScanner {

    private final String str;
    private int cursor;

    /**
     * creates a separate and duplicate StringScanner
     * @param other the StringScanner to duplicate
     */
    public StringScanner(StringScanner other) {
        this(other.str, other.cursor);
    }

    public StringScanner(String s) {
        this(s, 0);
    }

    public StringScanner(String str, int cursor) {
        this.str = str;
        this.cursor = cursor;
    }

    /**
     * @return true if there is anything left to read (if we are not at the end of the string)
     */
    public boolean hasNext() {
        return cursor < str.length();
    }

    /**
     * @return true if there is a digit left to read
     */
    public boolean hasNextDigit() {
        return hasNext() && Character.isDigit(peek());
    }

    /**
     * Gets the next character, and advances the cursor past it.
     *
     * This method does not perform safety checks before reading.
     * It is up to you to check hasNext() first.
     *
     * @return the next character
     */
    public char next() {
        return str.charAt(cursor++);
    }

    /**
     * Gets the next character without advancing the cursor.
     *
     * This method does not perform safety checks before reading.
     * It is up to you to check hasNext() first.
     *
     * @return the next character
     */
    public char peek() {
        return str.charAt(cursor);
    }

    /**
     * Reads a BigInteger, stopping on the first character that is not a digit,
     * and advances the cursor past it.
     *
     * This method does not check if a digit is available to read.
     *
     * @return a BigInteger read at the cursor
     */
    public BigInteger nextBigInteger() {
        StringBuilder steve = new StringBuilder();
        while (hasNextDigit()) {
            steve.append(next());
        }
        return new BigInteger(steve.toString());
    }

    private static final BigInteger MAX_LONG = new BigInteger(String.valueOf(Long.MAX_VALUE));

    /**
     * Reads a long, stopping on the first character that is not a digit,
     * and advances the cursor past it.
     *
     * This method does not check if a digit is available to read.
     *
     * @return the next long
     * @throws InvalidExpressionException if the next number is too large
     */
    public long nextLong() throws InvalidExpressionException {
        BigInteger bint = nextBigInteger();
        if (bint.compareTo(MAX_LONG) > 0) {
            throw new InvalidExpressionException(bint.toString() + " is too large");
        }
        return bint.longValue();
    }

    /**
     * Reads past any whitespace at the cursor
     */
    public void skipWhitespace() {
        while (hasNext() && Character.isWhitespace(peek())) {
            next();
        }
    }

    /**
     * @return the location of the cursor. This does not change the cursor.
     */
    public int getCursor() {
        return cursor;
    }

    /**
     * @param position sets the cursor position to the given value
     */
    public void setCursor(int position) {
        if (position >= 0 && position < str.length()) {
            this.cursor = position;
        }
    }
}
