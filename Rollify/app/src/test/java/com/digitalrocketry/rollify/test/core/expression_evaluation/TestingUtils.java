package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.RandomProvider;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class TestingUtils {
    public static final RandomProvider MIN = new RandomProvider() {
        @Override
        public long nextLong(long min, long max) {
            return min;
        }
    };

    public static final RandomProvider MAX = new RandomProvider() {
        @Override
        public long nextLong(long min, long max) {
            return max;
        }
    };
}
