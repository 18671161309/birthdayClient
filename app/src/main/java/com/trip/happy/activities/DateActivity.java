package com.trip.happy.activities;


import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.trip.core.utils.SPUtils;
import com.trip.core.utils.TimeUtil;
import com.trip.happy.R;
import com.trip.happy.activities.base.BaseActivity;
import com.trip.happy.constant.Constants;
import com.trip.happy.view.FishDrawableView;

import java.util.Date;

import butterknife.BindView;


public class DateActivity extends BaseActivity {


    @BindView(R.id.mark)
    TextView mark;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.shimmer_tv)
    ShimmerTextView shimmerTv;
    @BindView(R.id.two)
    LinearLayout two;
    @BindView(R.id.one)
    FishDrawableView one;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private long endTime;
    private long currentTime;
    private long differenceTime;

    @Override
    public int setLayoutId() {
        return R.layout.activity_date;
    }


    @Override
    public void initData() {
       boolean init = (boolean) SPUtils.get(Constants.INIT_DATE,true);
       if (init){
           initView();
       }else{
           startActivityFinish(SplashActivity.class);
       }

    }

    private void initView() {
        endTime = Long.valueOf((TimeUtil.getTimestamp("2017-12-5 00:00:00", "yyyy-MM-dd HH:mm:ss")));
        currentTime = new Date().getTime();
        differenceTime = endTime - currentTime;
        new CountDownTimer(differenceTime, 1000) {

            @Override
            public void onTick(long l) {
                date.setText(TimeUtil.formatSecond(l));
            }

            @Override
            public void onFinish() {
                two.setVisibility(View.VISIBLE);
                one.setVisibility(View.GONE);
                Shimmer shimmer = new Shimmer();
                shimmer.start(shimmerTv);
            }
        }.start();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog();
            }
        });
    }

    private void initDialog() {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("以上界面只会出现一次，请确认进入")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityFinish(SplashActivity.class);
                SPUtils.put(Constants.INIT_DATE,false);
            }
        }).show();

    }


}
