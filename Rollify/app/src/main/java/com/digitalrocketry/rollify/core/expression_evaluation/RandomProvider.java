package com.digitalrocketry.rollify.core.expression_evaluation;

/**
 * Created by David Aaron Suddjian on 9/4/2015.
 *
 * This interface allows tests to use their own RandomProviders for testing.
 */
public interface RandomProvider {

    public long nextLong(long min, long max);
}
