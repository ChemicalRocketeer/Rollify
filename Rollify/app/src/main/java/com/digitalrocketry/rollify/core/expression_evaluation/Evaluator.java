package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class Evaluator {

    private static List<Tokenizer> tokenizers = Arrays.asList(
            new NumberTokenizer(), new OperatorTokenizer(), new DieDefTokenizer(), new ParenTokenizer()
    );

    public Result evaluate(String expression) {
        TokenizationContext context = new TokenizationContext(new StringScanner(expression), tokenizers);
        return evaluate(context);
    }

    public Result evaluate(TokenizationContext context) {
        return evaluate(context.tokenize());
    }

    public Result evaluate(List<Token> postfixExpression) {
        if (postfixExpression.isEmpty()) return new Result(0);
        Stack<Long> stack = new Stack<>();
        for (Token t : postfixExpression) {
            t.operate(stack);
        }
        if (stack.size() != 1) throw new InvalidExpressionException();
        return new Result(stack.pop());
    }

    /**
     * @param numberToken must be a token for which isNumber() == true,
     *                   that puts a number on the stack without taking anything off the stack.
     */
    public Result evaluate(Token numberToken) {
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