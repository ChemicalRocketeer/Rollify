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

import java.util.List;


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
        List<Formula> fList = Select.from(Formula.class).list();
        adapter = new FormulaAdapter(getContext(), fList.toArray(new Formula[fList.size()]));
        adapter.sort(Formula.COMPARE_BY_NAME);
        list.setAdapter(adapter);
        return layout;
    }
}
