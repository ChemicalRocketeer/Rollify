package com.digitalrocketry.rollify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.digitalrocketry.rollify.db.Formula;


/**
 * A simple {@link Fragment} subclass.
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
        adapter = new FormulaAdapter(getContext(), new Formula[] {
                new Formula("Formula 1", "2d6+5d4+2", 0, 5),
                new Formula("Formula 2", "d4d12", 0, 3),
                new Formula("Formula 3", "3(2d10)", 0, 1),
        });
        adapter.sort(Formula.COMPARE_BY_NAME);
        list.setAdapter(adapter);
        return layout;
    }


}
