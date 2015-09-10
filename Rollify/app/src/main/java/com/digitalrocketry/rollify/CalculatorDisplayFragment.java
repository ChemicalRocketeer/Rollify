package com.digitalrocketry.rollify;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorDisplayFragment extends Fragment {

    TextView displayText;
    EditText expressionEditor;

    public CalculatorDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_calculator_display, container, false);
        expressionEditor = (EditText) layout.findViewById(R.id.calcExpressionEdit);
        displayText = (TextView) layout.findViewById(R.id.calcExpressionDisplay);
        return layout;
    }

    public void setDisplayText(String text) {
        displayText.setText(text);
    }

    public String getEditorText() {
        if (expressionEditor == null) {
            throw new IllegalStateException("Cannot find the expression editor text");
        }
        return expressionEditor.getText().toString();
    }
}
