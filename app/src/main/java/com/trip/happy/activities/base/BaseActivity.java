package com.trip.happy.activities.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


import com.trip.happy.R;
import com.trip.happy.view.RoundAngleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xiajun on 2017/11/20.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.iv_header_layout_back)
    public ImageView ivHeaderLayoutBack;
    @BindView(R.id.riv_header_layout_icon)
    public RoundAngleImageView rivHeaderLayoutIcon;
    @BindView(R.id.tv_header_layout_title)
    public TextView tvHeaderLayoutTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        initData();
    }

    public abstract void initData();

    public abstract int setLayoutId();


    public void startActivityFinish(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    public void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
