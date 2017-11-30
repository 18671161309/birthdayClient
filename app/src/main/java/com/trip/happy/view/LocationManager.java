package com.trip.happy.view;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;


/**
 * Created by Administrator on 2017/9/20.
 */

public class LocationManager {

    private static LocationManager manager;
    private static Context mContext;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    public PostAmapLocationListener mListener;

    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;


    public void setAmapLocation(PostAmapLocationListener listener) {
        this.mListener = listener;
    }


    private LocationManager() {
        initData();
    }

    public static void init(Context context) {
        mContext = context;
    }


    public static LocationManager getSingLeton() {
        if (manager == null) {
            manager = new LocationManager();
        }
        return manager;
    }

    private void initData() {
        mLocationClient = new AMapLocationClient(mContext);
        mLocationClient.setLocationListener(mapLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);


    }


    public WeatherSearch getWeatherSearch(String address, WeatherSearch.OnWeatherSearchListener listener) {
        mquery = new WeatherSearchQuery(address, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(mContext);
        mweathersearch.setQuery(mquery);
        mweathersearch.setOnWeatherSearchListener(listener);
        mweathersearch.searchWeatherAsyn();
        return mweathersearch;
    }


    public void startLocation() {
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        mLocationClient.stopLocation();

    }


    public AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    mListener.postData(amapLocation);
                } else {
                    mListener.postDataError(amapLocation.getErrorCode());
                }

                mLocationClient.stopLocation();
            }
        }
    };


    public interface PostAmapLocationListener {
        public void postData(AMapLocation amapLocation);

        public void postDataError(int code);
    }



    public String remark(int code) {
        if (code < 12) {
            return "夏先生温馨提示:请祝小敏女士:\n帽子：毛线帽或不带\n" +
                    "手套：棉织制品手套或不带\n" +
                    "围巾：毛线围巾、丝质围巾、针织围巾\n" +
                    "外套：皮衣、薄棉外套、风衣、棉衣、厚外套\n" +
                    "打底：保暖内衣、薄打底衫、打底毛衫\n" +
                    "裤子：牛仔裤、休闲裤、打底裤、铅笔裤\n" +
                    "鞋子：短靴、高筒靴、棉鞋\n" + "袜子：羊毛袜、粗毛线袜、棉袜";
        } else if (code < 15) {
            return "夏先生温馨提示:请祝小敏女士:\n帽子：毛线帽或不带\n" +
                    "手套：棉线手套、皮手套或不带\n" +
                    "围巾：丝质围巾\n" +
                    "外套：薄外套、针织衫、卫衣、牛仔衬衫\n" +
                    "打底：衬衫、长袖T桖、短袖T桖、薄毛衣\n" +
                    "裤子：牛仔裤、休闲裤\n" +
                    "鞋子：皮鞋、运动鞋、靴子\n"
                    + "袜子：羊毛袜、粗毛线袜、棉袜";
        }else if (code<17){
            return "夏先生温馨提示:请祝小敏女士:\n帽子：套头针织帽、棒球帽或不带\n" +
                    "外套：薄外套、运动外套、风衣、开衫毛衣\n" +
                    "打底：短袖、衬衫、卫衣、长袖T恤、薄毛衣\n" +
                    "裤子：牛仔裤、休闲裤、裙子（加保暖裤）\n" +
                    "鞋子：休闲鞋、皮鞋、球鞋、单鞋\n" +
                    "袜子：薄袜、短袜";
        }else if (code<19){
            return "夏先生温馨提示:请祝小敏女士:\n帽子：套头针织帽、棒球帽或不带\n" +
                    "手套：无需\n" +
                    "外套：薄外套、运动外套、风衣、开衫毛衣\n" +
                    "打底：短袖、坎肩、短袖衬衫、卫衣、长袖T恤\n" +
                    "裤子：牛仔裤、休闲裤、裙子（加保暖裤）\n" +
                    "鞋子：休闲鞋、皮鞋、球鞋、单鞋\n" +
                    "袜子：薄袜、短袜";
        }else if (code<21){
            return "夏先生温馨提示:请祝小敏女士:\n帽子：套头针织帽、棒球帽或不带\n" +
                    "手套：无需\n" +
                    "外套：薄外套、运动外套、开衫毛衣、马甲、风衣\n" +
                    "打底：短袖、坎肩、衬衫、卫衣、长袖T恤\n" +
                    "裤子：牛仔裤、休闲裤、裙子（加保暖裤）、布长裙\n" +
                    "鞋子：休闲鞋、皮鞋、球鞋、单鞋\n" +
                    "袜子：薄袜、短袜";
        }else if (code<25){
            return "夏先生温馨提示:请祝小敏女士:\n帽子：套头针织帽、棒球帽或不带\n" +
                    "手套：无需\n" +
                    "外套：薄外套、运动外套、马甲、风衣\n" +
                    "打底：短袖、坎肩、衬衫、卫衣、长袖T恤\n" +
                    "裤子：牛仔裤、休闲裤、短裙（加保暖裤）、布长裙\n" +
                    "鞋子：休闲鞋、皮鞋、球鞋、单鞋\n" +
                    "袜子：薄袜、短袜";
        }else {
            return "夏先生温馨提示:请祝小敏女士:\n天气炎热，请注意多喝水";
        }

    }

}
