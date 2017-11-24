package com.trip.happy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.trip.happy.R;
import com.trip.happy.activities.LoginActivity;
import com.trip.happy.fragment.base.LazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xiajun on 2017/11/20.
 */

public class SettingFragment extends LazyFragment {



    @BindView(R.id.btn_setting_logout)
    Button btnSettingLogout;

    public Object setLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView(View rootView) {

    }

    protected boolean isInit = false;

    @Override
    public void LazyLoad() {
        if (isInit && isVisible) {
            initListener();
        }
    }

    private void initListener() {
        btnSettingLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
        LazyLoad();
    }




}
