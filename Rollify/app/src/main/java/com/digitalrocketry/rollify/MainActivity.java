package com.digitalrocketry.rollify;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.tasks.EvaluationTask;

public class MainActivity extends AppCompatActivity implements EvaluationTask.Listener {

    CalculatorDisplayFragment calcDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void evaluateAndDisplay(View v) {
        if (calcDisplay == null) {
            calcDisplay = (CalculatorDisplayFragment) getFragmentManager().findFragmentById(R.id.calcDisplayFragment);
        }
        String expression = calcDisplay.getEditorText();
        new EvaluationTask().addListener(this).execute(expression);
    }

    @Override
    public void onEvaluationTaskComplete(EvaluationTask task) {
        calcDisplay.setDisplayText(task.getResult());
    }
}
