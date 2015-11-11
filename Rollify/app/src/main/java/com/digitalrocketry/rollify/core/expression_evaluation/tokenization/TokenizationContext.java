package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 *
 * The TokenizationContext converts an infix dice expression into a postfix
 * list of tokens. It does this by using a shunting-yard algorithm, modified so that it works with random numbers.
 * and interacts with Tokenizers which convert individual pieces from a StringScanner into Token objects.
 *
 * Tokenizers communicate with a TokenizationContext by pushing and pulling tokens from output and the stack,
 * following principles from Dijkstra's shunting-yard algorithm.
 *
 * A TokenizationContext can be created inside the parentheses of another TokenizationContext, to evaluate
 * the expression in parentheses independently
 */
public class TokenizationContext {

    private StringScanner scanner;
    private List<Tokenizer> tokenizers;
    private List<Token> output;
    private Stack<Operator> stack;
    private Token lastToken;
    private boolean finished;

    public TokenizationContext(String expression, List<Tokenizer> tokenizers) {
        this(new StringScanner(expression), tokenizers);
    }

    public TokenizationContext(TokenizationContext other) {
        this(other.scanner, other.tokenizers);
    }

    public TokenizationContext(StringScanner scanner, List<Tokenizer> tokenizers) {
        this.scanner = scanner;
        this.tokenizers = tokenizers;
        this.output = new LinkedList<>();
        this.stack = new Stack<>();
        this.lastToken = null;
        this.finished = false;
    }

    /**
     * Converts this TokenizationContext's expression (in infix order) into a list of Tokens (in postfix order),
     * using the Tokenizers provided at construction.
     *
     * @return a list of the Tokens in the expression in postfix order
     * @throws InvalidExpressionException if the expression is invalid
     */
    public List<Token> tokenize() throws InvalidExpressionException {
        scanner.skipWhitespace();
        this.finished = false;
        while (scanner.hasNext() && !finished) {
            boolean currentTokenConsumed = false;
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
        while (stack.size() > 0) {
            output.add(stack.pop());
        }
        return output;
    }

    /**
     * Pushes the given Token to the output
     *
     * @param t the token
     */
    public void pushToOutput(Token t) {
        output.add(t);
        lastToken = t;
    }

    /**
     * Pushes the given Operator to the operator stack
     *
     * @param op the Operator
     */
    public void pushToStack(Operator op) {
        float precedence = op.getPrecedence();
        while (stack.size() > 0 && stack.peek().getPrecedence() >= precedence) {
            output.add(stack.pop());
        }
        stack.push(op);
        lastToken = op;
    }

    /**
     * Signals the end of tokenization, as if the end of the expression had been reached.
     */
    public void finish() {
        finished = true;
    }

    /**
     * @return the top element from output without removing it
     */
    public Token peekOutput() {
        int index = output.size() - 1;
        return index < 0 ? null : output.get(output.size() - 1);
    }

    /**
     * @return the top element from output, while removing it.
     */
    public Token popOutput() {
        int index = output.size() - 1;
        return index < 0 ? null : output.remove(index);
    }

    /**
     * @return the top element from the stack without removing it
     */
    public Operator peekStack() {
        return stack.empty() ? null : stack.peek();
    }

    /**
     * @return the top element from the stack, while removing it
     */
    public Operator popStack() {
        return stack.empty() ? null : stack.pop();
    }

    /**
     * @return the last token found
     */
    public Token getLastToken() {
        return lastToken;
    }

    /**
     * @return whether the last token was a number. This performs null checks, while getLastToken() doesn't
     */
    public boolean lastTokenWasNumber() {
        return lastToken != null && lastToken.isNumber();
    }

    /**
     * @return whether the last token was an Operator. This performs null checks, while getLastToken() doesn't
     */
    public boolean lastTokenWasOperator() {
        return lastToken != null && lastToken instanceof Operator;
    }
}
