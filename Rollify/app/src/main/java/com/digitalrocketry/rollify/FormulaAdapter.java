package com.digitalrocketry.rollify;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digitalrocketry.rollify.db.Formula;
import com.orm.query.Select;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter for the formula list view
 */
public class FormulaAdapter extends BaseAdapter{

    private Select<Formula> query;
    private List<Formula> formulaList;
    private Comparator<Formula> comparator;
    private Context context;

    public FormulaAdapter(Context context, Select<Formula> query) {
        this(context, query, Formula.COMPARE_BY_NAME);
    }

    public FormulaAdapter(Context context, Select<Formula> query, Comparator<Formula> comparator) {
        this.context = context;
        this.query = query;
        this.comparator = comparator;
        this.formulaList = query.list();
    }

    public void setComparator(Comparator<Formula> comparator) {
        this.comparator = comparator;
    }

    public void setQuery(Select<Formula> selector) {
        this.query = selector;
    }

    @Override
    public void notifyDataSetChanged() {
        formulaList = query.list();
        Collections.sort(formulaList, comparator);
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.formula_list_item, null);
        }
        String name = getItem(position).name;
        ((TextView) v.findViewById(R.id.formulaName)).setText(name);
        return v;
    }

    @Override
    public int getCount() {
        return formulaList.size();
    }

    @Override
    public Formula getItem(int position) {
        return formulaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    @Override
    public boolean isEmpty() {
        return formulaList.isEmpty();
    }
}
