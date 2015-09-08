package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.Random;

/**
 * Created by David Aaron Suddjian on 9/4/2015.
 */
public class StandardRandomProvider implements RandomProvider {

    private static final Random RAND = new Random(System.currentTimeMillis());

    @Override
    public long nextLong(long min, long max) {
        if (min == max) {
            return max;
        }
        long diff = max - min;
        return RAND.nextLong() % diff + min ;
    }
}
