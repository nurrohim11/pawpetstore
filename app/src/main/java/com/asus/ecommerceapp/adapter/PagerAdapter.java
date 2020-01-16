package com.asus.ecommerceapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.asus.ecommerceapp.fragment.HistoryGroomingFragment;
import com.asus.ecommerceapp.fragment.HistoryOrderFragment;
import com.asus.ecommerceapp.fragment.HistoryPenitipanFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                HistoryOrderFragment historyOrderFragment = new HistoryOrderFragment();
                return historyOrderFragment;
            case 1:
                HistoryGroomingFragment historyGroomingFragment = new HistoryGroomingFragment();
                return historyGroomingFragment;
            case 2:
                HistoryPenitipanFragment historyPenitipanFragment = new HistoryPenitipanFragment();
                return historyPenitipanFragment;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}