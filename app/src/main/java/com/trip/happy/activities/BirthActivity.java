package com.trip.happy.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.Button;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.trip.happy.R;
import com.trip.happy.activities.base.BaseActivity;
import com.trip.happy.view.SnowUtils;
import com.trip.happy.view.SnowView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiajun on 2017/12/1.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

public class BirthActivity extends BaseActivity {

    @BindView(R.id.snowView)
    SnowView snowView;
    @BindView(R.id.shimmer_tv)
    ShimmerTextView desc;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public void initData() {
        snowView.startSnowAnim(SnowUtils.SNOW_LEVEL_HEAVY);
        Shimmer shimmer = new Shimmer();
        shimmer.start(desc);

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_birth;
    }



    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        startActivityFinish(SplashActivity.class);
    }
}
