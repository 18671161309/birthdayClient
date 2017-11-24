package com.trip.happy.activities;



import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.trip.happy.R;
import com.trip.happy.activities.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by xiajun on 2017/11/24.
 */

public class PrePicActivity extends BaseActivity {
    String url;
    @BindView(R.id.img)
    PhotoView info;


    @Override
    public void initData() {
        info.enable();
        info.setAnimaDuring(800);
        url = getIntent().getStringExtra("url");
        Glide
                .with(this)
                .load(url).into(info);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_pre_pic;
    }



}
