package com.example.vaiterapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.vaiterapp.R;
import com.example.vaiterapp.StaffTab1;
import com.example.vaiterapp.StaffTab2;
import com.example.vaiterapp.tab1;
import com.example.vaiterapp.tab2;
import com.example.vaiterapp.tab3;

public class SectionPagerAdapterStaff extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionPagerAdapterStaff(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                StaffTab1 tab1 = new StaffTab1();
                return tab1;
            case 1:
                StaffTab2 tab2 = new StaffTab2();
                return tab2;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Orders";
            case 1:
                return "History";


        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

