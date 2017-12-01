package com.trip.happy.activities;


import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.trip.core.utils.SPUtils;
import com.trip.core.utils.TimeUtil;
import com.trip.happy.R;
import com.trip.happy.activities.base.BaseActivity;
import com.trip.happy.constant.Constants;
import com.trip.happy.view.FishDrawableView;

import java.util.Date;

import butterknife.BindView;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DateActivity extends BaseActivity {


    @BindView(R.id.mark)
    TextView mark;
    @BindView(R.id.one)
    FishDrawableView one;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.image)
    SVGAImageView image;
    private SVGAParser parser;
    @BindView(R.id.floataction_button)
    TextView imageView;

    private long endTime;
    private long currentTime;
    private long differenceTime;

    private boolean isInit = true;


    @Override
    public int setLayoutId() {
        return R.layout.activity_date;
    }


    @Override
    public void initData() {
        boolean init = (boolean) SPUtils.get(Constants.INIT_DATE, true);
        if (init) {
            endTime = Long.valueOf((TimeUtil.getTimestamp(Constants.END_TIME, Constants.END_SIMFORMT)));
            currentTime = new Date().getTime();
            differenceTime = endTime - currentTime;
            initView();
        } else {
            startActivityFinish(SplashActivity.class);
        }

    }

    private void setLayout(String svga) {
        image.stopAnimation();
        parser = new SVGAParser(this);
        parser.parse(svga, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                image.setImageDrawable(drawable);
                image.startAnimation();
            }

            @Override
            public void onError() {

            }
        });


    }

    private void initView() {
        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long l) {
                date.setText(TimeUtil.formatSecond(l));
                if (l <= 3600000) {
                    if (isInit) {
                        setLayout("rose_2.0.0.svga");
                        isInit = false;
                    }
                } else if (l <= 10800000) {
                    if (isInit) {
                        setLayout("rose_1.5.0.svga");
                        isInit = false;
                    }

                } else if (l <= 14400000) {
                    if (isInit) {
                        setLayout("posche.svga");
                        isInit = false;
                    }
                } else {
                    if (isInit) {
                        setLayout("angel.svga");
                        isInit = false;
                    }
                }
            }

            @Override
            public void onFinish() {
                startActivityFinish(BirthActivity.class);
//                SPUtils.put(Constants.INIT_DATE, false);
            }
        }.start();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new Date().getTime() <= 3600000) {
                    initDialog(TimeUtil.formatSecond(endTime - new Date().getTime()), Constants.ONE_HOUR);
                } else if (new Date().getTime() <= 7200000) {
                    initDialog(TimeUtil.formatSecond(endTime - new Date().getTime()), Constants.TWO_HOUR);
                } else if (new Date().getTime() <= 10800000) {
                    initDialog(TimeUtil.formatSecond(endTime - new Date().getTime()), Constants.THREE_HOUR);
                } else {
                    initDialog(TimeUtil.formatSecond(endTime - new Date().getTime()), Constants.OTHER_MESSAGE);
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        image.stopAnimation();
    }

    private void initDialog(String time, String message) {
        new AlertDialog.Builder(this)
                .setMessage("距离你生日还有:" + time + "," + message)
                .setCancelable(false)
                .setPositiveButton("确认", null).show();

    }


    //    @BindView(R.id.btn_login)
//    Button btnLogin;
    //    @BindView(R.id.shimmer_tv)
//    ShimmerTextView shimmerTv;
//    @BindView(R.id.two)
//    LinearLayout two;


//                two.setVisibility(View.VISIBLE);
////                one.setVisibility(View.GONE);
//                Shimmer shimmer = new Shimmer();
//                shimmer.start(shimmerTv);


//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                initDialog();
//            }
//        });
}


//    private void initDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("温馨提示")
//                .setMessage("以上界面只会出现一次，请确认进入")
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                startActivityFinish(SplashActivity.class);
//                SPUtils.put(Constants.INIT_DATE, false);
//            }
//        }).show();
//
//    }



