package com.trip.happy.delegates;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.trip.core.utils.ToastUtils;
import com.trip.happy.R;
import com.trip.happy.activities.LoginActivity;
import com.trip.happy.adpter.MyPagerAdapter;
import com.trip.happy.delegates.base.HappyDelegate;
import com.trip.happy.fragment.BirthFragment;
import com.trip.happy.fragment.BlessFragment;
import com.trip.happy.fragment.LoveFragment;
import com.trip.happy.fragment.SmileFragment;
import com.trip.happy.view.LocationManager;
import com.trip.happy.view.RoundAngleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by xiajun on 2017/11/20.
 */

public class HomeDelegate extends HappyDelegate implements
        LocationManager.PostAmapLocationListener{


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
    @BindView(R.id.header_container)
    RelativeLayout headerContainer;
    @BindView(R.id.go_out)
    TextView goOut;
    @BindView(R.id.tv_menu_weather_city)
    TextView tvMenuWeatherCity;
    @BindView(R.id.tv_menu_weather_temperature)
    TextView tvMenuWeatherTemperature;

    @BindView(R.id.mark)
    TextView mMark;
    private List<Fragment> mFragment;


    @Override
    public Object setLayout() {
        return R.layout.delegate_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        tvHeaderLayoutTitle.setText("敏之恋");
        LocationManager.getSingLeton().setAmapLocation(this);
        initLeftContent();
        initRightContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationManager.getSingLeton().startLocation();
    }

    private void initLeftContent() {

    }

    @Override
    public void onPause() {
        super.onPause();
        LocationManager.getSingLeton().stopLocation();
    }

    private void initRightContent() {
        mFragment = new ArrayList<>();
        mFragment.add(LoveFragment.getInstance());
        mFragment.add(BirthFragment.getInstance());
        mFragment.add(BlessFragment.getInstance());
        mFragment.add(SmileFragment.getInstance());

        vpHorizontalNtb.setAdapter(new MyPagerAdapter(getFragmentManager(), null, mFragment));

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.taoxin),
                        Color.parseColor(colors[0]))
                        .title("照片墙")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.shengri),
                        Color.parseColor(colors[1]))
                        .title("生日宴")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.zhufu),
                        Color.parseColor(colors[2]))
                        .title("时间轴")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.weixiao),
                        Color.parseColor(colors[3]))
                        .title("微微笑")
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
        goOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getProxyActivity(), LoginActivity.class);
                startActivity(i);
                getProxyActivity().finish();
            }
        });
    }


    @Override
    public void postData(AMapLocation amapLocation) {
        tvMenuWeatherCity.setText("当前城市:\n\n" + amapLocation.getAddress());
        LocationManager.getSingLeton().getWeatherSearch(amapLocation.getCity(), new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                if (rCode == 1000) {
                    if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                        tvMenuWeatherTemperature.setText("当前天气: "+weatherLiveResult.getLiveResult().getWeather()+
                                "\n当前温度: "+weatherLiveResult.getLiveResult().getTemperature()+"°"+
                    "\n当前更新时间: "+weatherLiveResult.getLiveResult().getReportTime());
//
                        mMark.setText(LocationManager.getSingLeton().remark( Integer.valueOf(weatherLiveResult.getLiveResult().getTemperature())));
                    } else {
                        ToastUtils.showShortToast("没有查询到当前的天气");
                    }
                } else {
                    ToastUtils.showShortToast(rCode+"");
                }
            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

            }
        });

    }

    @Override
    public void postDataError(int code) {
        if (code == 12) {
            ToastUtils.showShortToast("请在设置中开启该应用权限");
            return;
        }
        ToastUtils.showShortToast("请求失败，请重试");
    }



}
