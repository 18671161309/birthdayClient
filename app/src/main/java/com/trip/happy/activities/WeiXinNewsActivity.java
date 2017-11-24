package com.trip.happy.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.trip.core.utils.CommonUtils;
import com.trip.happy.R;
import com.trip.happy.activities.base.BaseActivity;
import com.trip.happy.bean.WeiXinBean;
import com.trip.happy.constant.Constants;
import com.trip.happy.view.fab.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiajun on 2017/11/23.
 */

public class WeiXinNewsActivity extends BaseActivity {


    @BindView(R.id.pb_wei_xin_news_load)
    ProgressBar pbWeiXinNewsLoad;
    @BindView(R.id.wv_wei_xin_news_display)
    WebView wvWeiXinNewsDisplay;
    @BindView(R.id.fab_wei_xin_news_browser)
    FloatingActionButton fabWeiXinNewsBrowser;
    @BindView(R.id.fab_wei_xin_news_share)
    FloatingActionButton fabWeiXinNewsShare;
    private String url;


    @Override
    public int setLayoutId() {
        return R.layout.activity_wei_xin_news_layout;
    }

    @Override
    public void initData() {
        ivHeaderLayoutBack.setVisibility(View.VISIBLE);
        WebSettings webSettings = wvWeiXinNewsDisplay.getSettings();
        if (CommonUtils.isNetWorkAvailable()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        wvWeiXinNewsDisplay.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }
        });

        ivHeaderLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        wvWeiXinNewsDisplay.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pbWeiXinNewsLoad.setVisibility(View.GONE);
                } else {
                    if (pbWeiXinNewsLoad.getVisibility() == View.GONE) {
                        pbWeiXinNewsLoad.setVisibility(View.VISIBLE);
                    }
                    pbWeiXinNewsLoad.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return super.onJsAlert(view, url, message, result);
            }
        });

        WeiXinBean bean = (WeiXinBean) getIntent().getSerializableExtra(Constants.WEIXIN_BEAN);
        url = bean.getUrl();
        wvWeiXinNewsDisplay.loadUrl(url);
        tvHeaderLayoutTitle.setText("开心时刻");

    }

    @Override
    protected void onResume() {
        super.onResume();
        wvWeiXinNewsDisplay.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wvWeiXinNewsDisplay.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wvWeiXinNewsDisplay != null) {
            ((ViewGroup) wvWeiXinNewsDisplay.getParent()).removeView(wvWeiXinNewsDisplay);
            wvWeiXinNewsDisplay.destroy();
            wvWeiXinNewsDisplay = null;
        }
    }



    @OnClick({R.id.fab_wei_xin_news_browser, R.id.fab_wei_xin_news_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_wei_xin_news_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
            case R.id.fab_wei_xin_news_share:
                break;
        }
    }
}
