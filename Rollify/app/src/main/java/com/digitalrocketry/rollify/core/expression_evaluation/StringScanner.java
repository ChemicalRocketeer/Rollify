package com.digitalrocketry.rollify.core.expression_evaluation;

import java.math.BigInteger;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public class StringScanner {

    private final String s;
    private int cursor;

    public StringScanner(StringScanner other) {
        this(other.s, other.cursor);
    }

    public StringScanner(String s) {
        this(s, 0);
    }

    public StringScanner(String s, int cursor) {
        this.s = s;
        this.cursor = cursor;
    }

    public boolean hasNext() {
        return cursor < s.length();
    }

    public char next() {
        return s.charAt(cursor++);
    }

    public char peek() {
        return s.charAt(cursor);
    }

    public BigInteger nextBigInteger() {
        StringBuilder steve = new StringBuilder();
        while (Character.isDigit(peek())) {
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
}
