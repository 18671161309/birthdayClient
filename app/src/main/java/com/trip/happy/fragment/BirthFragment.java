package com.trip.happy.fragment;


import android.view.View;

import com.trip.happy.R;
import com.trip.happy.fragment.base.LazyFragment;

/**
 * Created by xiajun on 2017/11/20.
 */

public class BirthFragment extends LazyFragment {


    public Object setLayout() {
        return R.layout.fragment_birth;
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
