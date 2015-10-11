package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

import java.math.BigInteger;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class StringScanner {

    private final String str;
    private int cursor;

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

    public boolean hasNext() {
        return cursor < str.length();
    }

    public char next() {
        return str.charAt(cursor++);
    }

    public char peek() {
        return str.charAt(cursor);
    }

    public BigInteger nextBigInteger() {
        StringBuilder steve = new StringBuilder();
        while (hasNext() && Character.isDigit(peek())) {
            steve.append(next());
        }
        return new BigInteger(steve.toString());
    }

    private static final BigInteger MAX_LONG = new BigInteger(String.valueOf(Long.MAX_VALUE));

    public long nextLong() {
        BigInteger bint = nextBigInteger();
        if (bint.compareTo(MAX_LONG) > 0) {
            throw new InvalidExpressionException(bint.toString() + " is too large");
        }
        return bint.longValue();
    }

    public void skipWhitespace() {
        while (hasNext() && Character.isWhitespace(peek())) {
            next();
        }
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int position) {
        if (position >= 0 && position < str.length()) {
            this.cursor = position;
        }
    }
}
