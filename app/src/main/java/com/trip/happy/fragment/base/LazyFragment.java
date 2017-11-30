package com.trip.happy.fragment.base;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/3/29.
 */

public abstract class LazyFragment extends Fragment {

    protected boolean isVisible = false;
    private Unbinder unbinder = null;
    public ProgressDialog mDialog;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }
    }

    public abstract Object setLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;

        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("setLayout() type must be int or view");
        }

        unbinder = ButterKnife.bind(this, rootView);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("请稍候....");
        mDialog.setCancelable(false);
        initView(rootView);
        return rootView;
    }

    public abstract void initView(View rootView);

    private void onInVisible() {

    }

    private void onVisible() {
        LazyLoad();
    }

    public abstract void LazyLoad();


    public AlertDialog customDialog(int layoutId) {

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        View layout = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        dialog.setView(layout, 0, 0, 0, 0);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(layoutId);
        return dialog;
    }


}
