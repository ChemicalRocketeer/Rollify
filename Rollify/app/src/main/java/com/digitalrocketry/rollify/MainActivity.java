package com.digitalrocketry.rollify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.digitalrocketry.rollify.tasks.EvaluationTask;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements EvaluationTask.Listener {

    CalculatorDisplayFragment calcDisplay;
    Map<Integer, String> numpadMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numpadMap = new HashMap<>();
        numpadMap.put(R.id.calcNumpad0, "0");
        numpadMap.put(R.id.calcNumpad1, "1");
        numpadMap.put(R.id.calcNumpad2, "2");
        numpadMap.put(R.id.calcNumpad3, "3");
        numpadMap.put(R.id.calcNumpad4, "4");
        numpadMap.put(R.id.calcNumpad5, "5");
        numpadMap.put(R.id.calcNumpad6, "6");
        numpadMap.put(R.id.calcNumpad7, "7");
        numpadMap.put(R.id.calcNumpad8, "8");
        numpadMap.put(R.id.calcNumpad9, "9");
        numpadMap.put(R.id.calcNumpadOpenParen, "(");
        numpadMap.put(R.id.calcNumpadCloseParen, ")");
        calcDisplay = (CalculatorDisplayFragment) getFragmentManager().findFragmentById(R.id.calcDisplayFragment);
    }

    public void evaluateAndDisplay(View v) {
        String expression = calcDisplay.getEditorText();
        new EvaluationTask().addListener(this).execute(expression);
    }

    @Override
    public void onEvaluationTaskBegin(EvaluationTask task) {
        calcDisplay.setDisplayText("evaluating...");
    }

    @Override
    public void onEvaluationTaskComplete(EvaluationTask task) {
        calcDisplay.setDisplayText(task.getResult());
    }

    public void onNumpadButtonPressed(View view) {
        String str = numpadMap.get(view.getId());
        if (str != null) {
            calcDisplay.insertTextAtCursor(str);
        }
    }
}
