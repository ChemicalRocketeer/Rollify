package com.digitalrocketry.rollify.core.expression_evaluation;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class ExpressionUtils {

    // this isn't final because tests need to be able to change it
    public static RandomProvider RAND = new StandardRandomProvider();

    public static final long MAX_DIE_COUNT = 1000000;
    public static final long MAX_DIE_TYPE = 1000000000;

    public static final Token findMultiplierToken(TokenizationContext context) {
        if (context.lastTokenWasnumber()) {
            Token last = context.getLastToken();
            List<Token> tokens = context.getOutputTokens();
            tokens.remove(tokens.size() - 1);
            return last;
        } else {
            return new NumberToken(1);
        }
    }
}
