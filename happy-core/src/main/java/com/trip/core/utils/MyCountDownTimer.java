package com.trip.core.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by xiajun on 2017/11/20.
 */

public class MyCountDownTimer extends CountDownTimer {

    private TextView ts;
    private FinishCount listener;

    public MyCountDownTimer(TextView textView, long millisInFuture, long countDownInterval,FinishCount count) {
        super(millisInFuture, countDownInterval);
        this.ts = textView;
        this.listener = count;
    }

    @Override
    public void onTick(long l) {
        ts.setText("倒计时\n" + l / 1000 + "");

    }

    @Override
    public void onFinish() {
        ts.setText("跳转中");
        listener.finish();
    }

    public interface FinishCount{
        void finish();
    }
}
