package com.trip.happy.activities;

import android.view.View;
import android.widget.TextView;

import com.trip.core.utils.MyCountDownTimer;
import com.trip.happy.R;
import com.trip.happy.activities.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by xiajun on 2017/11/20.
 */

public class SplashActivity extends BaseActivity {


    @BindView(R.id.count_down)
    TextView countDown;
    private MyCountDownTimer timer;

    @Override
    public void initData() {
        timer = new MyCountDownTimer(countDown, 6000, 1000, new MyCountDownTimer.FinishCount() {
            @Override
            public void finish() {
                startActivityFinish(LoginActivity.class);
            }
        });
        timer.start();
        onClick();
    }

    private void onClick() {
        countDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                startActivityFinish(LoginActivity.class);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }


}
