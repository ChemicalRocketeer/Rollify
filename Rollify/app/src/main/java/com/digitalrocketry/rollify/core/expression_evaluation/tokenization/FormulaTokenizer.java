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

    private Stack<String> nestedFormulas = new Stack<>();

    @Override
    public boolean tryTokenize(TokenizationContext context, StringScanner scanner)
            throws InvalidExpressionException {
        checkForCloseChar(scanner);
        if (scanner.next() == OPEN_CHAR) {
            String formulaName = scanner.readUntil(CLOSE_CHAR);
            if (formulaName == null) {
                // if formulaName is null, then the scanner didn't find a closing bracket
                throw new InvalidExpressionException(MISMATCHED_BRACKETS_MSG);
            }
            ensureSafeNestedFormulas(formulaName);
            // we have the correct syntax, so try to find the formula
            Formula f = formulaProvider.getFormula(formulaName);
            if (f == null) {
                throw new InvalidExpressionException(UNKNOWN_FORMULA_MSG + ": " + formulaName);
            }
            // we have found a valid formula! Time to process it!
            Token coefficient = ExpressionUtils.findCoefficientToken(context);
            TokenGroup expr = new TokenGroup(coefficient,
                    new TokenizationContext(f.getExpression(), context).tokenize());
            context.pushToOutput(expr);
            return true;
        }
        return false;
    }

    private void ensureSafeNestedFormulas(String formulaName) throws InvalidExpressionException {
        if (nestedFormulas.contains(formulaName)) {
            throw new InvalidExpressionException(SELF_REFERENTIAL_FORMULA_MSG);
        } else {
            nestedFormulas.add(formulaName);
        }
    }

    private static void checkForCloseChar(StringScanner s) throws InvalidExpressionException {
        if (s.peek() == CLOSE_CHAR) throw new InvalidExpressionException(MISMATCHED_BRACKETS_MSG);
    }
}
