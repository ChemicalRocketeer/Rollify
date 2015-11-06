package com.digitalrocketry.rollify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digitalrocketry.rollify.db.Formula;

/**
 * Adapter for the formula list view
 */
public class FormulaAdapter extends ArrayAdapter<Formula> {

    public FormulaAdapter(Context context, Formula[] formulas) {
        super(context, R.layout.formula_list_item, formulas);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.formula_list_item, null);
        }
        String name = getItem(position).name;
        ((TextView) v.findViewById(R.id.formulaName)).setText(name);
        return v;
    }
}
