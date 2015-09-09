package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class TokenizationContext {

    private StringScanner scanner;
    private List<Tokenizer> tokenizers;
    private boolean inParentheses;
    private List<Token> output;
    private Stack<Operator> opStack;
    private Token lastToken;
    private boolean currentTokenConsumed;
    private boolean finished;

    public TokenizationContext(StringScanner scanner, List<Tokenizer> tokenizers) {
        this(scanner, tokenizers, false);
    }

    public TokenizationContext(StringScanner scanner, List<Tokenizer> tokenizers, boolean inParentheses) {
        this.scanner = scanner;
        this.tokenizers = tokenizers;
        this.inParentheses = inParentheses;
        this.output = new LinkedList<>();
        this.opStack = new Stack<>();
        this.lastToken = null;
        this.currentTokenConsumed = false;
        this.finished = false;
    }

    public List<Token> tokenize() {
        scanner.skipWhitespace();
        this.finished = false;
        while (scanner.hasNext() && !finished) {
            currentTokenConsumed = false;
            Iterator<Tokenizer> it = tokenizers.iterator();
            while (it.hasNext() && !currentTokenConsumed) {
                // If the current tokenizer consumes a token,
                // leave the cursor position where that tokenizer left off. Otherwise we
                // reset the position for the next tokenizer.
                int cursorPosition = scanner.getCursor();
                if (it.next().tryTokenize(this, scanner)) {
                    currentTokenConsumed = true;
                } else {
                    scanner.setCursor(cursorPosition);
                }
            }
            if (!currentTokenConsumed) throw new InvalidExpressionException("Invalid symbol: " + scanner.peek());
            scanner.skipWhitespace();
        }
        while (opStack.size() > 0) {
            output.add(opStack.pop());
        }
        return output;
    }

    public void commitToken(Token t) {
        output.add(t);
        lastToken = t;
    }

    public void commitOperator(Operator op) {
        float precedence = op.getPrecedence();
        while (opStack.size() > 0 && opStack.peek().getPrecedence() >= precedence) {
            output.add(opStack.pop());
        }
        opStack.push(op);
        lastToken = op;
    }

    public void finish() {
        finished = true;
    }

    public List<Token> getOutputTokens() {
        return output;
    }

    public Stack<Operator> getOpStack() {
        return opStack;
    }

    public Token getLastToken() {
        return lastToken;
    }

    public boolean lastTokenWasnumber() {
        return lastToken.isNumber();
    }

    public boolean isInParentheses() {
        return inParentheses;
    }
}
