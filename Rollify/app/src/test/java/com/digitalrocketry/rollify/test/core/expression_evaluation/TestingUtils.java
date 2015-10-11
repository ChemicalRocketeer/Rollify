package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokens.NumberToken;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.RandomProvider;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;

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

    /**
     * Always consumes a token regardless of the input provided. If the last token consumed was a number,
     * commits Operator.ADD, else commits a NumberToken(1);
     */
    public static final Tokenizer ONE_OR_ADD = new Tokenizer() {
        @Override
        public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
            sc.next();
            if (context.lastTokenWasnumber()) {
                context.commitOperator(Operator.ADD);
            } else {
                context.commitToken(new NumberToken(1));
            }
            return true;
        }
    };
}
