package com.digitalrocketry.rollify.test.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokens.IntegerToken;
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
     * commits Operator.ADD, else commits a IntegerToken(1);
     */
    public static final Tokenizer ONE_OR_ADD = new Tokenizer() {
        @Override
        public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
            sc.next();
            if (context.lastTokenWasNumber()) {
                context.pushToStack(Operator.ADD);
            } else {
                context.pushToOutput(new IntegerToken(1));
            }
            return true;
        }
    };
}
