package com.digitalrocketry.rollify.core.expression_evaluation;

import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.DieDefTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.IntegerTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.OperatorTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.ParenTokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.StringScanner;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.TokenizationContext;
import com.digitalrocketry.rollify.core.expression_evaluation.tokenization.Tokenizer;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 *
 * Evaluates a given expression into a Result.
 *
 * Expressions are expected to be in postfix notation. Behavior with expressions in other notations
 * is undefined.
 *
 * If an invalid expression (or an invalid sequence of tokens) is supplied, an InvalidExpressionException
 * will be thrown with details.
 *
 * An example expression might be: 1 + 2 - 3 * 4 / 2(2d6d20 + 12)
 */
public class Evaluator {

    private static List<Tokenizer> tokenizers = Arrays.asList(
            new IntegerTokenizer(), new OperatorTokenizer(), new DieDefTokenizer(), new ParenTokenizer()
    );

    public Result evaluate(String expression)
            throws  InvalidExpressionException {
        TokenizationContext context = new TokenizationContext(expression, tokenizers);
        return evaluate(context);
    }

    /**
     * Evaluates the given TokenizationContext and returns the result.
     *
     * @param context the context containing the expression information
     * @return the result of the evaluation
     * @throws InvalidExpressionException if the expression is invalid
     */
    public Result evaluate(TokenizationContext context)
            throws  InvalidExpressionException {
        return evaluate(context.tokenize());
    }

    /**
     * Evaluates the given list of Tokens as an expression and returns the result
     *
     * @param postfixExpression a list of Tokens in postfix order
     * @return the result of the evaluation
     * @throws InvalidExpressionException if the expression is invalid
     */
    public Result evaluate(List<Token> postfixExpression)
            throws  InvalidExpressionException {
        if (postfixExpression.isEmpty()) return new Result(0);
        Stack<Long> stack = new Stack<>();
        try {
            for (Token t : postfixExpression) {
                t.operate(stack);
            }
        } catch (EmptyStackException e) {
            throw new InvalidExpressionException();
        }
        if (stack.size() != 1) throw new InvalidExpressionException();
        return new Result(stack.pop());
    }

    /**
     * Evaluates an individual token and returns the result.
     *
     * @param numberToken must be a token for which isNumber() == true,
     *                    which puts a number on the stack without taking anything off the stack.
     */
    public Result evaluate(Token numberToken)
            throws  InvalidExpressionException {
        if (numberToken.isNumber()) {
            Stack<Long> stack = new Stack<>();
            try {
                numberToken.operate(stack);
            } catch (EmptyStackException e) {
                throw new InvalidExpressionException("invalid expression " + numberToken.toString());
            }
            if (stack.size() != 1) throw new InvalidExpressionException("invalid expression" + numberToken.toString());
            return new Result(stack.pop());
        } else {
            throw new InvalidExpressionException("invalid expression " + numberToken.toString());
        }
    }
}