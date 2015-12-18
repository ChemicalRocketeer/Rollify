package com.digitalrocketry.rollify;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.digitalrocketry.rollify.tasks.EvaluationTask;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements EvaluationTask.Listener {

    public static final String ACTION_EDIT_EXPRESSION = "com.digitalrocketry.rollify.EDIT_EXPRESSION";
    public static final String EXTRA_EXPRESSION = "com.digitalrocketry.rollify.EXISTING_EXPRESSION";

    // the mode that we are in, MAIN is for rolling and displaying results as the main activity,
    // EDITING_EXPRESSION is for editing an expression and returning it as a result to another activity
    private enum MODE {
        MAIN, EDITING_EXPRESSION
    }
    private MODE mode = MODE.MAIN;

    private String originalExpression = null;

    CalculatorDisplayFragment calcDisplay;
    Map<Integer, String> entryButtonMap; // map keypad buttons to their expression text values
    private ViewPager formulaListPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        entryButtonMap = new HashMap<>();
        entryButtonMap.put(R.id.calcButton0, "0");
        entryButtonMap.put(R.id.calcButton1, "1");
        entryButtonMap.put(R.id.calcButton4, "4");
        entryButtonMap.put(R.id.calcButton2, "2");
        entryButtonMap.put(R.id.calcButton3, "3");
        entryButtonMap.put(R.id.calcButton5, "5");
        entryButtonMap.put(R.id.calcButton6, "6");
        entryButtonMap.put(R.id.calcButton7, "7");
        entryButtonMap.put(R.id.calcButton8, "8");
        entryButtonMap.put(R.id.calcButton9, "9");
        entryButtonMap.put(R.id.calcButtonOpenParen, "(");
        entryButtonMap.put(R.id.calcButtonCloseParen, ")");
        entryButtonMap.put(R.id.calcButtonAdd, "+");
        entryButtonMap.put(R.id.calcButtonSub, "-");
        entryButtonMap.put(R.id.calcButtonMul, "*");
        entryButtonMap.put(R.id.calcButtonDiv, "/");
        entryButtonMap.put(R.id.calcButtonAdvD, "d");
        entryButtonMap.put(R.id.calcButtonAdvH, "h");
        entryButtonMap.put(R.id.calcButtonAdvL, "l");
        entryButtonMap.put(R.id.calcButtonAdvF, "df");
        entryButtonMap.put(R.id.calcButtonD2, "d2");
        entryButtonMap.put(R.id.calcButtonD4, "d4");
        entryButtonMap.put(R.id.calcButtonD6, "d6");
        entryButtonMap.put(R.id.calcButtonD8, "d8");
        entryButtonMap.put(R.id.calcButtonD10, "d10");
        entryButtonMap.put(R.id.calcButtonD12, "d12");
        entryButtonMap.put(R.id.calcButtonD20, "d20");
        entryButtonMap.put(R.id.calcButtonD100, "d100");

        calcDisplay = (CalculatorDisplayFragment) getFragmentManager()
                .findFragmentById(R.id.calcDisplayFragment);
        FormulaListFragment fListFrag = (FormulaListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.calcFormulaListFragment);
        fListFrag.setFormulaUser(calcDisplay);

        formulaListPager = (ViewPager) findViewById(R.id.formula_view_pager);

        Intent intent = getIntent();
        if (intent.getAction().equals(ACTION_EDIT_EXPRESSION)) {
            // we are editing an expression and should return an expression string to the calling activity
            mode = MODE.EDITING_EXPRESSION;
            originalExpression = intent.getStringExtra(EXTRA_EXPRESSION);
            calcDisplay.setEditorText(originalExpression);
        } else {
            mode = MODE.MAIN;
        }
    }

    @Override
    public void onBackPressed() {
        if (formulaListPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            formulaListPager.setCurrentItem(formulaListPager.getCurrentItem() - 1);
        }
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_backspace:
                Log.i("Rollify menu", "backspace pressed");
                calcDisplay.backspaceAtCursor();
                return true;
            case R.id.add_formula:
                Log.i("Rollify menu", "add formula pressed");
                Intent intent = new Intent(this, FormulaDetailsActivity.class);
                intent.putExtra(FormulaDetailsActivity.EXPRESSION_MESSAGE, calcDisplay.getEditorText());
                startActivity(intent);
                return true;
            case R.id.action_clear_expression:
                Log.i("Rollify menu", "action settings pressed");
                calcDisplay.clearExpression();
                return true;
            case R.id.action_settings:
                Log.i("Rollify menu", "action settings pressed");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void evaluateExpressionAndDisplay() {
        String expression = calcDisplay.getEditorText();
        new EvaluationTask().addListener(this).execute(expression);
    }

    public void returnExpressionToPreviousActivity() {
        String expression = calcDisplay.getEditorText();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EXPRESSION, expression);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCalcEqualsButtonPressed(View view) {
        if (mode == MODE.EDITING_EXPRESSION) {
            returnExpressionToPreviousActivity();
        } else {
            evaluateExpressionAndDisplay();
        }
    }

    @Override
    public void onEvaluationTaskBegin(EvaluationTask task) {
        //calcDisplay.setDisplayText("evaluating...");
    }

    @Override
    public void onEvaluationTaskComplete(EvaluationTask task) {
        calcDisplay.setDisplayText(task.getResult());
    }

    public void onCalcEntryButtonPressed(View view) {
        String str = entryButtonMap.get(view.getId());
        if (str != null) {
            calcDisplay.insertTextAtCursor(str);
        }
    }

    public void onCalcNumpadButtonPressed(View view) {
        onCalcEntryButtonPressed(view);
    }

    public void onCalcOperatorButtonPressed(View view) {
        onCalcEntryButtonPressed(view);
    }

    public void onCalcAdvancedButtonPressed(View view) {
        onCalcEntryButtonPressed(view);
    }

    public void onCalcDButtonPressed(View view) {
        onCalcEntryButtonPressed(view);
    }
}
