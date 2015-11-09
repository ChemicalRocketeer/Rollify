package com.digitalrocketry.rollify;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    public interface FormulaUser {
        void useFormula(Formula f);
    }

    private FormulaAdapter adapter;
    private FormulaUser formulaUser;

    public FormulaListFragment() {
        // Required empty public constructor
    }

    public FormulaListFragment setFormulaUser(FormulaUser user) {
        this.formulaUser = user;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
        registerForContextMenu(list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                formulaUser.useFormula(adapter.getItem(position));
            }
        });
        return layout;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.formulaListView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(adapter.getItem(info.position).getName());
            for (String option : new String[] { "Use", "Edit", "Delete" }) {
                menu.add(option);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Formula f = adapter.getItem(info.position);
        switch (item.getItemId()) {
            case 0:
                Log.i("Rollify", "context item case 0");
                if (formulaUser != null) {
                    formulaUser.useFormula(f);
                }
                break;
            case 1:
                Log.i("Rollify", "context item case 1");
                startFormulaDetailsActivity(f);
                break;
            case 2:
                Log.i("Rollify", "context item case 2");
                f.delete();
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }

    public void startFormulaDetailsActivity(Formula f) {
        Intent intent = new Intent(getContext(), FormulaDetailsActivity.class);
        intent.putExtra(FormulaDetailsActivity.ID_MESSAGE, f.getId());
        startActivity(intent);
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
