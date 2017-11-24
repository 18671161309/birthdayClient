package com.trip.happy.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.trip.core.app.Happy;

import cn.bmob.v3.Bmob;


/**
 * Created by xiajun on 2017/11/17.
 */

public class HappyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Bmob.initialize(this, "4c3206c9aa101d865aee99f66ae0776b");
        Happy.init(this)
                .withApiHost("https://api.tianapi.com/wxnew/?key=96af073984a8a84160cf36994e3ffa92&num=20")
                .configure();
    }
}
