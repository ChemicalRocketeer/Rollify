package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.DefaultRandomProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by David Aaron Suddjian on 9/10/2015.
 */
public class DefaultRandomProviderTest {

    @Test
    public void testConstrain() throws Exception {
        List<Integer> results = new ArrayList<>(8000);
        for (int i = -4000; i < 4000; i++) {
            int v = (int) DefaultRandomProvider.constrain(i, 2, 9);
            results.add(v);
            assertTrue(v >= 2);
            assertTrue(v <= 9);
        }
        int[] distribution = new int[10];
        for (int i = 0; i < results.size(); i++) {
            distribution[results.get(i)] ++;
        }
        int targetCount = results.size() / 8;
        for (int i = 2; i < distribution.length; i++) {
            assertEquals(targetCount, distribution[i]);
        }
    }
}
