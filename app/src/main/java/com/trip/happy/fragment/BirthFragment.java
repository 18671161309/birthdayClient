package com.trip.happy.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.trip.core.recyclerView.CommonAdapter;
import com.trip.core.recyclerView.base.ViewHolder;
import com.trip.core.recyclerView.wrapper.HeaderAndFooterWrapper;
import com.trip.core.utils.ToastUtils;
import com.trip.happy.R;
import com.trip.happy.bean.Friends;
import com.trip.happy.fragment.base.LazyFragment;
import com.trip.happy.service.MicService;
import com.trip.happy.view.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import tyrantgit.widget.HeartLayout;


/**
 * Created by xiajun on 2017/11/20.
 */

public class BirthFragment extends LazyFragment {


    private Timer task;
    private Random mRandom = new Random();
    private List<Drawable> images = new ArrayList<>();
    @BindView(R.id.heart_layout)
    HeartLayout heartLayout;
    Banner banner;
    protected boolean isInit = false;
    @BindView(R.id.list_card)
    RecyclerView listCard;


    private CommonAdapter<Friends> commonAdapter;
    private HeaderAndFooterWrapper<Friends> mAdapter;
    private List<Friends> mFriends;

    public Object setLayout() {
        return R.layout.fragment_birth;
    }

    public static BirthFragment getInstance() {
        BirthFragment fragment = new BirthFragment();
        return fragment;
    }

    @Override
    public void initView(View rootView) {

        if (task != null) {
            task.cancel();
        }
        task = new Timer();
        banner = new Banner(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        banner.setLayoutParams(params);

        images.add(getContext().getResources().getDrawable(R.drawable.image1));
        images.add(getContext().getResources().getDrawable(R.drawable.image2));
        images.add(getContext().getResources().getDrawable(R.drawable.image3));
        images.add(getContext().getResources().getDrawable(R.drawable.lg_bg));

        listCard.setLayoutManager(new LinearLayoutManager(getActivity()));
        listCard.setItemAnimator(new DefaultItemAnimator());
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CENTER);
        //设置图片集合
        banner.setImages(images);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.CubeOut);
        banner.isAutoPlay(true);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.CENTER);


        commonAdapter = new CommonAdapter<Friends>(getContext(), R.layout.recyclerview_birth, mFriends) {
            @Override
            protected void convert(ViewHolder holder, final Friends friend, int position) {
                holder.setImageUrl(R.id.image_header, friend.getHeaderUrl().getUrl());
                holder.setText(R.id.content, friend.getBless_statement());
                holder.setText(R.id.name, friend.name);


                holder.setOnClickListener(R.id.phone, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + friend.getFriend_phone()));
                        startActivity(intentPhone);
                    }
                });

                holder.setOnClickListener(R.id.message, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLayoutMessage(friend.getFriend_phone(), friend.getName());
                    }
                });
            }
        };
        mAdapter = new HeaderAndFooterWrapper<>(commonAdapter);
        banner.start();

        mAdapter.addHeaderView(banner);
        listCard.setAdapter(mAdapter);


        initListener();
    }

    private AlertDialog dialogView;

    private void setLayoutMessage(final String phone, String name) {

        dialogView = customDialog(R.layout.dialog_message_layout);

        Button sendBtn = dialogView.findViewById(R.id.send);
        Button cancelBtn = dialogView.findViewById(R.id.cancel);
        TextView contract = dialogView.findViewById(R.id.contract);
        final EditText edit = dialogView.findViewById(R.id.write_message);

        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                dialogView.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });


        edit.requestFocus();
        contract.setText("发送给:" + name);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edit.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    ToastUtils.showShortToast("请输入短信内容");
                    return;
                }

                sendMessage(phone, message);

            }
        });
    }


    private void sendMessage(String phone, String message) {
        //处理返回的发送状态
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sendIntent = PendingIntent.getBroadcast(getActivity(), 0, sentIntent,
                0);
        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        ToastUtils.showShortToast("短信发送成功");
                        dialogView.dismiss();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        dialogView.dismiss();
                        ToastUtils.showShortToast("RESULT_ERROR_GENERIC_FAILURE");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        dialogView.dismiss();
                        ToastUtils.showShortToast("RESULT_ERROR_RADIO_OFF");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        dialogView.dismiss();
                        ToastUtils.showShortToast("RESULT_ERROR_NULL_PDU");
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));


        SmsManager manager = SmsManager.getDefault();
        ArrayList<String> list = manager.divideMessage(message);  //因为一条短信有字数限制，因此要将长短信拆分
        for (String text : list) {
            manager.sendTextMessage(phone, null, text, sendIntent, null);
        }


    }

    private void initData() {
        mDialog.show();
        BmobQuery<Friends> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Friends>() {
            @Override
            public void done(List<Friends> list, BmobException e) {
                mDialog.dismiss();
                if (e == null) {
                    mFriends = list;
                    commonAdapter.updatas(mFriends);
                }
            }
        });
    }


    @Override
    public void LazyLoad() {
        if (isInit && isVisible) {
            initData();
            startService();
        }
    }


    private void startService() {
        Intent intent = new Intent(getActivity(), MicService.class);
        getActivity().startService(intent);
    }


    private void initListener() {
        task.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        heartLayout.addHeart(randomColor());
                    }
                });
            }
        }, 500, 200);
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

    @Override
    public void onPause() {
        super.onPause();
        isInit = false;
        banner.stopAutoPlay();
        Intent intent = new Intent(getActivity(), MicService.class);
        getActivity().stopService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        isInit = true;
        banner.startAutoPlay();
        LazyLoad();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        task.cancel();

    }


}
