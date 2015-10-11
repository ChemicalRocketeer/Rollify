package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public interface Tokenizer {

    public boolean tryTokenize(TokenizationContext context, StringScanner tempScanner);
}
