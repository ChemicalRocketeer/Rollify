package com.digitalrocketry.rollify;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalrocketry.rollify.db.Formula;
import com.digitalrocketry.rollify.utils.Range;

import java.util.Stack;

public class CalculatorDisplayFragment extends Fragment implements FormulaListFragment.FormulaUser {

    class BackspaceListener implements View.OnTouchListener {

        Handler handler;
        int delay = 400;
        int initialDelay = 800;
        Stack<String> backspaceHistory = new Stack<>();

        Runnable action = new Runnable() {
            @Override
            public void run() {
                smartBackspace();
                handler.postDelayed(this, delay);
            }
        };

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (handler != null) return true;
                    backspaceHistory.push(backspace());
                    handler = new Handler();
                    handler.postDelayed(action, initialDelay);
                    break;
                case MotionEvent.ACTION_UP:
                    if (handler == null) return true;
                    handler.removeCallbacks(action);
                    handler = null;
                    backspaceHistory.clear();
                    break;
            }
            return false;
        }

        public void smartBackspace() {
            int start, end;
            int pos1 = Math.max(expressionEditor.getSelectionStart(), 0);
            int pos2 = Math.max(expressionEditor.getSelectionEnd(), 0);
            Log.i("cursor", "pos1: " + pos1 + " pos2: " + pos2);
            String text = getEditorText();
            if (pos1 != pos2) {
                // there is an existing selection
                start = Math.min(pos1, pos2);
                end = Math.max(pos1, pos2);
            } else {
                // there is no selection, we will figure out what to delete
                Range range = findSmartBackspaceRange(text, pos1);
                start = range.min;
                end = range.max + 1;
            }
            backspaceStack.push(text.substring(start, end));
            expressionEditor.getEditableText().delete(start, end);
            updateBackspaceVisibility();
        }


    }

    TextView displayText;
    EditText expressionEditor;
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
        backspaceButton.setOnTouchListener(new BackspaceListener());

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

    /**
     * deletes the currently selected text (or if there is no selection, the character in front of
     * the cursor), and returns the deleted text.
     *
     * @return the text that has been deleted
     */
    public String backspace() {
        int pos1 = Math.max(expressionEditor.getSelectionStart(), 0);
        int pos2 = Math.max(expressionEditor.getSelectionEnd(), 0);
        int start = Math.min(pos1, pos2);
        int end = Math.max(pos1, pos2);
        if (start == end && start > 0) {
            // the cursor is only at one position, so we move start back to make a selection
            start --;
        }
        String deletedText = getEditorText().substring(start, end);
        expressionEditor.getEditableText().delete(start, end);
        expressionEditor.setSelection(start, start);
        updateBackspaceVisibility();
        return deletedText;
    }

    /**
     * @param text the text in question
     * @param cursorIndex the index of the character after the cursor
     * @return a range of character indices to delete, inclusive,
     * or null if there is nothing to delete
     */
    public static Range findSmartBackspaceRange(String text, int cursorIndex) {
        if (cursorIndex <= 0 || cursorIndex > text.length()) return null; // nothing to delete
        int start, end;
        end = start = cursorIndex - 1; // by default only one character will be deleted
        char startChar = text.charAt(start);
        if (Character.isDigit(startChar)) {
            // the current character is part of a number and we should delete the entire number
            while (end < text.length() - 1 && Character.isDigit(text.charAt(end + 1))) {
                end++;
            }
            while (start > 0 && Character.isDigit(text.charAt(start - 1))) {
                start--;
            }
        } else if (startChar == ']') {
            // the character is a closing formula bracket and we should delete the formula
            // first we look for an opening bracket
            int i = start;
            while (i >= 0) {
                // if we find an opening bracket, we set the start to point at it. If not,
                // start is unchanged.
                if (text.charAt(i) == '[') {
                    start = i;
                    break;
                }
                i--;
            }
        } else if (startChar == '[') {
            // the character is an opening formula bracket and we should delete the formula
            // first we look for a closing bracket
            int i = end;
            while (i < text.length()) {
                // if we find a closing bracket, we set the end to point at it. If not,
                // end is unchanged.
                if (text.charAt(i) == ']') {
                    end = i;
                    break;
                }
                i++;
            }
        }
        return new Range(start, end);
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
