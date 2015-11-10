package com.digitalrocketry.rollify.core.expression_evaluation;

/**
 * Created by David Aaron Suddjian on 9/4/2015.
 *
 * This interface allows modification of where the random numbers come from, allowing different
 * kinds of calculations involving dice expressions. For example, you could have a RandomProvider
 * that only ever returns the max value, to test what the largest possible value is for a roll.
 */
public interface RandomProvider {

    long nextLong(long min, long max);
}
