package com.digitalrocketry.rollify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.digitalrocketry.rollify.tasks.EvaluationTask;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements EvaluationTask.Listener {

    CalculatorDisplayFragment calcDisplay;
    Map<Integer, String> entryButtonMap;

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
        calcDisplay = (CalculatorDisplayFragment) getFragmentManager().findFragmentById(R.id.calcDisplayFragment);
    }

    public void evaluateExpressionAndDisplay() {
        String expression = calcDisplay.getEditorText();
        new EvaluationTask().addListener(this).execute(expression);
    }

    public void onCalcEqualsButtonPressed(View view) {
        evaluateExpressionAndDisplay();
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
