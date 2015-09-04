package com.digitalrocketry.rollify.core.expression_evaluation;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/2/2015.
 */
public class Evaluator {

    public static Tokenizer numberTokenizer = new Tokenizer() {
        @Override
        public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
            if (Character.isDigit(sc.peek())) {
                context.commitToken(new NumberToken(sc.nextLong()));
                return true;
            } else {
                return false;
            }
        }
    };

    public static Tokenizer operatorTokenizer = new Tokenizer() {
        @Override
        public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
            String symbol = String.valueOf(sc.next());
            if (Operator.isDefined(symbol)) {
                Operator op = Operator.get(symbol);
                if (context.lastTokenWasnumber()) {
                    context.commitOperator(op);
                } else if (op.equals(Operator.SUB)) {
                    // this token might be negation instead of a SUB operator
                    sc.skipWhitespace();
                    // check expression validity
                    if (!sc.hasNext() || Operator.isDefined(sc.peek()))
                        throw new InvalidExpressionException("misplaced operator: " + op);
                    long num;
                    if (Character.isDigit(sc.peek())) {
                        num = sc.nextLong();
                    } else {
                        // if the next token is something other than a number then we want to commit a -1
                        num = 1;
                    }
                    context.commitToken(new NumberToken(-num));
                } else {
                    throw new InvalidExpressionException("misplaced operator: " + op);
                }
                return true;
            } else {
                return false;
            }
        }
    };

    public static Tokenizer dieDefTokenizer = new Tokenizer() {
        @Override
        public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
            if (sc.next() == 'd') {
                sc.skipWhitespace();
                if (!Character.isDigit(sc.peek()))
                    throw new InvalidExpressionException("No die type specified");
                long dieType = sc.nextLong();
                // if there was a number before the diedef, it is the diecount. Otherwise the diecount is 1
                Token dieCount;
                if (context.lastTokenWasnumber()) {
                    dieCount = context.getLastToken();
                } else {
                    dieCount = new NumberToken(1);
                }
                context.commitToken(new DieToken(dieCount, dieType));
                return true;
            } else {
                return false;
            }
        }
    };

    public static Tokenizer openParenTokenizer = new Tokenizer() {
        @Override
        public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
            if (sc.next() == '(') {
                List<Token> contents = new TokenizationContext(sc, tokenizers, true).tokenize();
                Token multiplier;
                if (context.lastTokenWasnumber()) {
                    multiplier = context.getLastToken();
                } else {
                    multiplier = new NumberToken(1);
                }
                context.commitToken(new TokenGroup(multiplier, contents));
                return true;
            } else {
                return false;
            }
        }
    };

    public static Tokenizer closeParenTokenizer = new Tokenizer() {
        @Override
        public boolean tryTokenize(TokenizationContext context, StringScanner sc) {
            if (context.isInParentheses() && sc.next() == ')') {
                context.finish();
                return true;
            }
            return false;
        }
    };

    private static List<Tokenizer> tokenizers = Arrays.asList(
            numberTokenizer, operatorTokenizer, dieDefTokenizer, openParenTokenizer, closeParenTokenizer
    );

    public Result evaluate(String expression) {
        TokenizationContext context = new TokenizationContext(new StringScanner(expression), tokenizers);
        return evaluate(expression, context);
    }

    public Result evaluate(String expression, TokenizationContext context) {
        return evaluate(new StringScanner(expression), context);
    }

    public Result evaluate(StringScanner expression, TokenizationContext context) {
        return evaluate(context.tokenize());
    }

    public Result evaluate(List<Token> postfixExpression) {
        Stack<Long> stack = new Stack<>();
        for (Token t : postfixExpression) {
            t.operate(stack);
        }
        if (stack.size() != 1) throw new InvalidExpressionException();
        return new Result(stack.pop());
    }
}