package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Operator;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 9/8/2015.
 *
 * Tokenizes parenthesized groups of tokens into one TokenGroup. This implementation was used
 * instead of the standard shunting-yard algorithm so that random variables in parentheses can have
 * different values if the expression in parentheses needs to be evaluated more than once (such as 4(5d6).
 */
public class ParenTokenizer implements Tokenizer {

    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner sc)
            throws InvalidExpressionException {
        if (sc.peek() == '(') {
            sc.next();
            // push tokens to the output and stack so we can find the open paren when we hit
            // the close paren
            Token multiplier = ExpressionUtils.findCoefficientToken(context);
            ParenControlToken parenToken = new ParenControlToken(multiplier);
            context.pushToOutput(parenToken);
            context.pushToStack(parenToken);
            return true;
        } else if (sc.peek() == ')') {
            sc.next();
            LinkedList<Token> contents = new LinkedList<>();
            while (!(context.peekOutput() instanceof ParenControlToken)) {
                Token toke = context.popOutput();
                if (toke == null) {
                    throw new InvalidExpressionException("mismatched parentheses");
                } else {
                    contents.addFirst(toke);
                }
            }
            context.popOutput(); // remove the paren token

            while (!(context.peekStack() instanceof ParenControlToken)) {
                Operator op = context.popStack();
                if (op == null) {
                    throw new InvalidExpressionException("mismatched parentheses");
                } else {
                    contents.addLast(op);
                }
            }
            ParenControlToken parenToken = (ParenControlToken) context.popStack(); // remove paren token

            context.pushToOutput(new TokenGroup(parenToken.coefficient, contents));
            return true;
        } else {
            return false;
        }
    }

    private static class ParenControlToken extends Operator {

        Token coefficient;

        public ParenControlToken(Token coefficient) {
            this.coefficient = coefficient;
        }

        @Override
        public boolean isControl() {
            return true;
        }

        @Override
        public float getPrecedence() {
            return -1;
        }

        @Override
        public String getSymbol() {
            return "(";
        }

        @Override
        public void operate(Stack<Long> stack) throws InvalidExpressionException {
            throw new InvalidExpressionException("mismatched parentheses");
        }
    }
}
