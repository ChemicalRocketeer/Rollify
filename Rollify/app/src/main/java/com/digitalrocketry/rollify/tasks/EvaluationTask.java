package com.digitalrocketry.rollify.tasks;

import android.os.AsyncTask;

import com.digitalrocketry.rollify.CalculatorDisplayFragment;
import com.digitalrocketry.rollify.core.expression_evaluation.Evaluator;
import com.digitalrocketry.rollify.core.expression_evaluation.InvalidExpressionException;
import com.digitalrocketry.rollify.core.expression_evaluation.Result;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by David Aaron Suddjian on 9/10/2015.
 */
public class EvaluationTask extends AsyncTask<String, Void, String> {

    public interface Listener {
        void onEvaluationTaskComplete(EvaluationTask task);
    }

    private List<Listener> listeners = new LinkedList<>();

    public EvaluationTask addListener(Listener l) {
        listeners.add(l);
        return this;
    }

    public EvaluationTask removeListener(Listener l) {
        listeners.remove(l);
        return this;
    }

    private String result;

    public String getResult() {
        return result;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 1)
            return null;
        try {
            Evaluator eval = new Evaluator();
            Result r = eval.evaluate(params[0]);
            result = String.valueOf(r.getResult());
        } catch (InvalidExpressionException e) {
            result = e.getMessage();
        } catch (Exception e) {
            result = "unknown error";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        for (Listener l : listeners) {
            l.onEvaluationTaskComplete(this);
        }
    }
}
