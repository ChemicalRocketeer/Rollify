package com.digitalrocketry.rollify;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CalcNumpadFragment extends Fragment {

    public CalcNumpadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calc_numpad_linear, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
