package com.trip.happy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trip.happy.R;
import com.trip.happy.fragment.base.BaseFragment;
import com.trip.happy.fragment.base.LazyFragment;

/**
 * Created by xiajun on 2017/11/20.
 */

public class BlessFragment extends LazyFragment {


    public Object setLayout() {
        return R.layout.fragment_bless;
    }

    @Override
    public void initView(View rootView) {

    }

    protected boolean isInit = false;
    @Override
    public void LazyLoad() {
        if (isInit && isVisible) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
        LazyLoad();
    }




}
