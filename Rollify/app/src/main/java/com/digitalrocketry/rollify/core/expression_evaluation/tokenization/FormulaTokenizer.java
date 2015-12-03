package com.digitalrocketry.rollify.core.expression_evaluation.tokenization;

import com.digitalrocketry.rollify.core.expression_evaluation.ExpressionUtils;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.Token;
import com.digitalrocketry.rollify.core.expression_evaluation.tokens.TokenGroup;
import com.digitalrocketry.rollify.db.Formula;

import java.util.Stack;

/**
 * Created by David Aaron Suddjian on 12/2/2015.
 *
 * Looks for formula names surrounded by square brackets. When it finds one,
 * it tokenizes its expression into a TokenGroup and pushes the TokenGroup to the context's output.
 *
 * If formulas reference each other, an InvalidExpressionException is thrown.
 */
public class FormulaTokenizer implements Tokenizer {

    public interface FormulaProvider {
        Formula getFormula(String name);
    }

    private static FormulaProvider formulaProvider = new FormulaProvider() {
        @Override
        public Formula getFormula(String name) {
            return Formula.findByName(name);
        }
    };

    public static void setFormulaProvider(FormulaProvider fp) {
        formulaProvider = fp;
    }

    public static final char OPEN_CHAR = '[';
    public static final char CLOSE_CHAR = ']';

    public static final String MISMATCHED_BRACKETS_MSG = "mismatched brackets";
    public static final String UNKNOWN_FORMULA_MSG = "unknown formula";
    public static final String SELF_REFERENTIAL_FORMULA_MSG = "self referential formula";

    // a stack of formula names, allowing us to keep track of how formula references are nested.
    // e.g. if [formula a] has the expression "1 + [formula b]" then formula be is nested
    // in formula a. We don't want to allow a self-referential formula nesting loop, so we have
    // to keep track of what is nested in what.
    private Stack<String> nestedFormulas = new Stack<>();

    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner scanner)
            throws InvalidExpressionException {
        // very important!! Any time this method throws an InvalidExpressionException,
        // it has to clear the nestedFormulas list or else future evaluations will be messed up
        if (scanner.peek() == CLOSE_CHAR) invalidExpression(MISMATCHED_BRACKETS_MSG);
        if (scanner.next() == OPEN_CHAR) {
            String formulaName = scanner.readUntil(CLOSE_CHAR);
            if (formulaName == null) invalidExpression(MISMATCHED_BRACKETS_MSG);
            // we have the correct syntax, so try to find the formula
            Formula f = formulaProvider.getFormula(formulaName);
            if (f != null) {
                // we have found a valid formula! Time to process it!
                if (nestedFormulas.contains(formulaName))
                    invalidExpression(SELF_REFERENTIAL_FORMULA_MSG + ": " + formulaName);
                Token coefficient = ExpressionUtils.findCoefficientToken(context);
                nestedFormulas.push(formulaName);
                try {
                    TokenGroup expr = new TokenGroup(coefficient,
                            new TokenizationContext(f.getExpression(), context).tokenize());
                    context.pushToOutput(expr);
                } catch (Exception e) {
                    nestedFormulas.pop();
                    throw e;
                }
                nestedFormulas.pop();
                return true;
            } else {
                invalidExpression(UNKNOWN_FORMULA_MSG + ": " + formulaName);
            }
        }
        return false;
    }

    private void invalidExpression(String message) throws InvalidExpressionException {
        throw new InvalidExpressionException(message);
    }
}
