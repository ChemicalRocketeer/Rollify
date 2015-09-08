package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 */
public class EvaluatorTest {

    private RandomProvider originalRandomProvider;

    @Before
    public void setUp() throws Exception {
        originalRandomProvider = ExpressionUtils.RAND;
    }

    @After
    public void tearDown() throws Exception {
        ExpressionUtils.RAND = originalRandomProvider;
    }

    @Test
    public void testEvaluate() throws Exception {

    }

    @Test
    public void testEvaluate1() throws Exception {

    }

    @Test
    public void testEvaluate2() throws Exception {

    }

    @Test
    public void testEvaluate3() throws Exception {

    }
}