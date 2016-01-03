package com.digitalrocketry.rollify;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalrocketry.rollify.db.Formula;

import java.util.Stack;

public class CalculatorDisplayFragment extends Fragment implements FormulaListFragment.FormulaUser {

    TextView displayText;
    TextView expressionEditor;
    View backspaceButton;
    Stack<String> backspaceStack;

    public CalculatorDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_calculator_display, container, false);
        expressionEditor = (EditText) layout.findViewById(R.id.calcExpressionEdit);
        displayText = (TextView) layout.findViewById(R.id.calcExpressionDisplay);
        backspaceButton = layout.findViewById(R.id.backspaceButton);
        backspaceButton.setOnTouchListener(new View.OnTouchListener() {

            Handler handler;
            int delay = 300;
            int initialDelay = 800;

            Runnable action = new Runnable() {
                @Override public void run() {
                    backspace();
                    handler.postDelayed(this, delay);
                }
            };

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (handler != null) return true;
                        backspace();
                        handler = new Handler();
                        handler.postDelayed(action, initialDelay);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (handler == null) return true;
                        handler.removeCallbacks(action);
                        handler = null;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (handler == null) return true;
                        handler.removeCallbacks(action);
                        handler = null;
                        break;
                }
                return false;
            }

        });
        updateBackspaceVisibility();
        backspaceStack = new Stack<>();
        if (expressionEditor.length() != 0) {
            backspaceStack.push(getEditorText());
        }
        return layout;
    }

    public void setDisplayText(String text) {
        displayText.setText(text);
    }

    public void setEditorText(String text) {
        expressionEditor.setText(text);
        backspaceStack.clear();
        updateBackspaceVisibility();
    }

    public String getEditorText() {
        return expressionEditor.getText().toString();
    }

    public void insertTextAtCursor(String text) {
        int start = Math.max(expressionEditor.getSelectionStart(), 0);
        int end = Math.max(expressionEditor.getSelectionEnd(), 0);
        expressionEditor.getEditableText().replace(Math.min(start, end), Math.max(start, end), text);
        if (isNumber(text)) {
            StringBuilder steve = new StringBuilder(text);
            while (!backspaceStack.empty() && isNumber(backspaceStack.peek())) {
                steve.insert(0, backspaceStack.pop());
            }
            text = steve.toString();
        }
        backspaceStack.push(text);
        updateBackspaceVisibility();
    }

    public void backspace() {
        int end = expressionEditor.length();
        int wordLength = backspaceStack.empty() ? 1 : backspaceStack.pop().length();
        int start = end - wordLength;
        start = Math.max(start, 0);
        expressionEditor.getEditableText().delete(start, end);
        updateBackspaceVisibility();
    }

    public void clearExpression() {
        expressionEditor.getEditableText().clear();
        backspaceStack.clear();
        updateBackspaceVisibility();
    }

    private void updateBackspaceVisibility() {
        if (expressionEditor.length() > 0) {
            backspaceButton.setVisibility(View.VISIBLE);
        } else {
            backspaceButton.setVisibility(View.GONE);
        }
    }

    private boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }

    @Override
    public void useFormula(Formula f) {
        insertTextAtCursor("[" + f.getName() + "]");
    }
}
