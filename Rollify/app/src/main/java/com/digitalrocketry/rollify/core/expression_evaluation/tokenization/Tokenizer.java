package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;

/**
 * Created by David Aaron Suddjian on 9/1/2015.
 */
public interface Tokenizer {

    /**
     * Tries to find a token in the expression where the scanner is currently pointing.
     *
     * It is important for subclasses to only read what they need from the StringScanner,
     * so that part or all of the next token is not consumed.
     *
     * @param context the context to use
     * @param scanner a stringscanner to look for token data
     * @return true if a valid token was found, else false
     */
    boolean tryTokenize(TokenizationContext context, StringScanner scanner)
            throws InvalidExpressionException;
}
