package com.digitalrocketry.rollify.core.expression_evaluation;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class Utils {

    // this isn't final because tests need to be able to change it
    public static RandomProvider RAND = new StandardRandomProvider();

    public static final long MAX_DIE_COUNT = 1000000;
    public static final long MAX_DIE_TYPE = 1000000000;
}
