package com.trip.happy;


import android.widget.Toast;

import com.trip.core.okHttp.OkHttpUtils;
import com.trip.core.okHttp.callback.StringCallback;
import com.trip.core.utils.ToastUtils;
import com.trip.happy.activities.base.BaseActivity;
import com.trip.happy.bean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;


/**
 * Created by xiajun on 2017/11/21.
 */

public class TestActivity extends BaseActivity {
    @Override
    public void initData() {


//        OkHttpUtils.get()
//                .url("https://api.tianapi.com/wxnew/?key=96af073984a8a84160cf36994e3ffa92&num=20")
//                .addParams("page","20")
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                String s = response;
//                ToastUtils.showLongToast(s);
//            }
//        });
//        RestClient.builder().url("https://api.tianapi.com/wxnew/?key=96af073984a8a84160cf36994e3ffa92&num=20").params("page", 10).showLoading(this).success(new ISuccess() {
//            @Override
//            public void success(String response) {
//                Toast.makeText(TestActivity.this, response, Toast.LENGTH_SHORT).show();
//            }
//        }).failure(new IFailure() {
//            @Override
//            public void failure() {
//                Toast.makeText(TestActivity.this, "失败", Toast.LENGTH_SHORT).show();
//            }
//        }).error(new IError() {
//            @Override
//            public void error(int code, String msg) {
//                Toast.makeText(TestActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        }).build().get();


//        BmobQuery<Pic> bmobQuery = new BmobQuery<>();
//        bmobQuery.findObjects(new FindListener<Pic>() {
//            @Override
//            public void done(List<Pic> list, BmobException e) {
//
//                if (e == null) {
//                    for (Pic p : list) {
//                        Toast.makeText(TestActivity.this, "天价成功", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
//
//        User user = new User();
//        user.setPassword("123455");
//        user.setUsername("xiajun");
//
//
//        user.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    Toast.makeText(TestActivity.this, "添加数据成功" + s, Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(TestActivity.this, "添加数据失败" + s, Toast.LENGTH_LONG).show();
//                }
//            }
//        });


    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_date;
    }
}
