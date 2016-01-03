package com.digitalrocketry.rollify;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalrocketry.rollify.db.Formula;

public class CalculatorDisplayFragment extends Fragment implements FormulaListFragment.FormulaUser {

    TextView displayText;
    EditText expressionEditor;
    View backspaceButton;

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
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backspaceAtCursor();
            }
        });
        updateBackspaceVisibility();
        return layout;
    }

    public void setDisplayText(String text) {
        displayText.setText(text);
    }

    public void setEditorText(String text) {
        expressionEditor.setText(text);
        updateBackspaceVisibility();
    }

    public String getEditorText() {
        return expressionEditor.getText().toString();
    }

    public void insertTextAtCursor(String text) {
        int start = Math.max(expressionEditor.getSelectionStart(), 0);
        int end = Math.max(expressionEditor.getSelectionEnd(), 0);
        Editable editable = expressionEditor.getEditableText();
        editable.replace(Math.min(start, end), Math.max(start, end), text);
        updateBackspaceVisibility();
    }

    public void backspaceAtCursor() {
        int pos1 = Math.max(expressionEditor.getSelectionStart(), 0);
        int pos2 = Math.max(expressionEditor.getSelectionEnd(), 0);
        int start = Math.min(pos1, pos2);
        int end = Math.max(pos1, pos2);
        if (start == end && start > 0) {
            // the cursor is only at one position, so we move start back to make a selection
            start --;
        }
        expressionEditor.getEditableText().delete(start, end);
        expressionEditor.setSelection(start, start);
        updateBackspaceVisibility();
    }

    public void clearExpression() {
        expressionEditor.getEditableText().clear();
        updateBackspaceVisibility();
    }

    private void updateBackspaceVisibility() {
        if (expressionEditor.length() > 0) {
            backspaceButton.setVisibility(View.VISIBLE);
        } else {
            backspaceButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void useFormula(Formula f) {
        insertTextAtCursor("[" + f.getName() + "]");
    }
}
