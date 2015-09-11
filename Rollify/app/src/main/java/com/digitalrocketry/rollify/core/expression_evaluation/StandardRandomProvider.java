package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.Random;

/**
 * Created by David Aaron Suddjian on 9/4/2015.
 */
public class StandardRandomProvider implements RandomProvider {

    private static final Random RAND = new Random(System.currentTimeMillis());

    @Override
    public long nextLong(long min, long max) {
        return constrain(RAND.nextLong(), min, max) ;
    }

    public static long constrain(long value, long min, long max) {
        if (min == max) {
            long temp = min;
            min = max;
            max = temp;
        }
        long diff = max - min;
        value = Math.abs(value);
        value %= diff + 1;
        value += min;
        return value;
    }
}
