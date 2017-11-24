package com.trip.happy.delegates;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trip.happy.R;
import com.trip.happy.adpter.MyPagerAdapter;
import com.trip.happy.delegates.base.HappyDelegate;
import com.trip.happy.fragment.BirthFragment;
import com.trip.happy.fragment.BlessFragment;
import com.trip.happy.fragment.LoveFragment;
import com.trip.happy.fragment.SettingFragment;
import com.trip.happy.fragment.SmileFragment;
import com.trip.happy.view.RoundAngleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by xiajun on 2017/11/20.
 */

public class HomeDelegate extends HappyDelegate {


    @BindView(R.id.vp_horizontal_ntb)
    ViewPager vpHorizontalNtb;
    @BindView(R.id.ntb_horizontal)
    NavigationTabBar ntbHorizontal;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.riv_header_layout_icon)
    RoundAngleImageView rivHeaderLayoutIcon;
    @BindView(R.id.tv_header_layout_title)
    TextView tvHeaderLayoutTitle;
    @BindView(R.id.iv_header_layout_right)
    ImageView ivHeaderLayoutRight;
    private List<Fragment> mFragment;


    @Override
    public Object setLayout() {
        return R.layout.delegate_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        tvHeaderLayoutTitle.setText("敏之恋");
        initLeftContent();
        initRightContent();
    }

    private void initLeftContent() {

    }

    private void initRightContent() {
        mFragment = new ArrayList<>();
        mFragment.add(new LoveFragment());
        mFragment.add(new BirthFragment());
        mFragment.add(new BlessFragment());
        mFragment.add(new SmileFragment());
        mFragment.add(new SettingFragment());

        vpHorizontalNtb.setAdapter(new MyPagerAdapter(getFragmentManager(),null, mFragment));

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.taoxin),
                        Color.parseColor(colors[0]))
                        .title("心愿")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.shengri),
                        Color.parseColor(colors[1]))
                        .title("生日")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.zhufu),
                        Color.parseColor(colors[2]))
                        .title("祝福")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.weixiao),
                        Color.parseColor(colors[3]))
                        .title("微笑")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.shezhi),
                        Color.parseColor(colors[4]))
                        .title("设置")
                        .build()
        );

        ntbHorizontal.setModels(models);
        ntbHorizontal.setViewPager(vpHorizontalNtb, 2);


        rivHeaderLayoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT, true);
            }
        });
    }


}
