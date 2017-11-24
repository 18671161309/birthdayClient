package com.trip.happy.activities;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.trip.core.utils.ToastUtils;
import com.trip.core.view.AutoEditText;
import com.trip.happy.MainActivity;
import com.trip.happy.R;
import com.trip.happy.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xiajun on 2017/11/20.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.iv_login_bg)
    ImageView ivLoginBg;
    @BindView(R.id.aet_login_name)
    AutoEditText aetLoginName;
    @BindView(R.id.aet_login_password)
    AutoEditText aetLoginPassword;
    @BindView(R.id.btn_login_confirm)
    Button btnLoginConfirm;


    @Override
    public void initData() {
        startAnimation();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }

    private void startAnimation() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override

            public void run() {
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.translate_anim);
                ivLoginBg.startAnimation(animation);
            }
        }, 200);
    }

    @OnClick(R.id.btn_login_confirm)
    public void onViewClicked() {
        if (TextUtils.isEmpty(aetLoginName.getText())) {
            aetLoginName.startShakeAnimation();
            ToastUtils.showShortToast(getString(R.string.account_null));
            return;
        }
        if (TextUtils.isEmpty(aetLoginPassword.getText())) {
            aetLoginPassword.startShakeAnimation();
            ToastUtils.showShortToast(getString(R.string.password_null));
            return;
        }


        //TODO 请求服务器数据
        startActivityFinish(MainActivity.class);

    }
}
