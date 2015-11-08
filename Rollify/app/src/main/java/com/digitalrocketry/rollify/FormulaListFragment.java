package com.digitalrocketry.rollify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.digitalrocketry.rollify.db.Formula;
import com.orm.query.Select;

import java.util.Comparator;
import java.util.List;


/**
 * The fragment containing the Formula List
 */
public class FormulaListFragment extends Fragment {

    private FormulaAdapter adapter;

    public FormulaListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_formula_list, container, false);
        ListView list = (ListView) layout.findViewById(R.id.formulaListView);
        adapter = new FormulaAdapter(getContext(), Select.from(Formula.class), Formula.COMPARE_BY_NAME);
        list.setAdapter(adapter);
        if (adapter.isEmpty()) {
            new Formula("Test Formula 1", "2d12+d4d6").save();
            new Formula("Test Formula 2", "6(d20)").save();
            new Formula("Test Formula 3", "8 + 200d2").save();
            updateFormulaView();
        }
        return layout;
    }

    public void updateFormulaView() {
        adapter.notifyDataSetChanged();
    }

    public void setSortingMode(Comparator<Formula> comparator) {
        adapter.setComparator(comparator);
    }

    public void setFormulaQuery(Select<Formula> selector) {
        adapter.setQuery(selector);
    }
}
