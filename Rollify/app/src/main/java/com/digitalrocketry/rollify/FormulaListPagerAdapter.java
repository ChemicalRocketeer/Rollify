package com.digitalrocketry.rollify;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A simple pager adapter designed for a very small number of pages.
 * Do not use with large numbers of pages!
 */
public class FormulaListPagerAdapter extends FragmentPagerAdapter {

    Fragment[] fragments;

    public FormulaListPagerAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
}
