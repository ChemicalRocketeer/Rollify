package com.digitalrocketry.rollify.core.tokenization;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class TokenizationContext {

    private List<Tokenizer> tokenizers;
    private StringScanner scanner;
    private List<Token> output;
    private Stack<Operator> opStack;
    private boolean lastTokenWasNumber;
    private boolean currentTokenIsNumber;
    private boolean currentTokenConsumed;

    public TokenizationContext(StringScanner scanner, List<Tokenizer> tokenizers) {
        this.tokenizers = tokenizers;
        this.scanner = scanner;
        this.output = new LinkedList<>();
        this.opStack = new Stack<>();
        this.lastTokenWasNumber = false;
        this.currentTokenIsNumber = false;
        this.currentTokenConsumed = false;
    }

    public List<Token> tokenize() {
        scanner.skipWhitespace();
        while (scanner.hasNext()) {
            currentTokenConsumed = false;
            currentTokenIsNumber = false;
            Iterator<Tokenizer> it = tokenizers.iterator();
            while (it.hasNext() && !currentTokenConsumed) {
                it.next().tryTokenize(this);
            }
            lastTokenWasNumber = currentTokenIsNumber;
            scanner.skipWhitespace();
        }
        return output;
    }

    public void commit() {
        currentTokenConsumed = true;
    }

    public void commitToken(Token t) {
        output.add(t);
        commit();
    }

    public void commitOperator(Operator o) {

        commit();
    }

    public List<Token> getOutputTokens() {
        return output;
    }

    public StringScanner getScanner() {
        return scanner;
    }

    public boolean lastTokenWasnumber() {
        return lastTokenWasNumber;
    }

    public void setIsTokenNumber(boolean value) {
        currentTokenIsNumber = value;
    }
}
