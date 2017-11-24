package com.trip.happy.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xiajun on 2017/11/20.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitleList;

    public MyPagerAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
        this.mTitleList = titleList;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList == null ? null : mTitleList.get(position);
    }
}
