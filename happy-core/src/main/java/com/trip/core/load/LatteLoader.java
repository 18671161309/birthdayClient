package com.trip.core.load;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


import com.trip.core.utils.DimenUtils;
import com.trip.hcore.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by xiajun on 2017/10/19.
 */

public class LatteLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFER_SIZE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoadStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoadStyle> styleEnum) {
        showLoading(context, styleEnum.name());
    }

    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView indicatorView = LoadCreator.create(type, context);
        dialog.setContentView(indicatorView);

        int deviceWidth = DimenUtils.getScreenWidth();
        int deviceHeight = DimenUtils.getScreenHeight();

        final Window window = dialog.getWindow();

        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = deviceWidth / LOADER_SIZE_SCALE;
            params.height = deviceHeight / LOADER_SIZE_SCALE;
            params.height = params.height + deviceHeight / LOADER_OFFER_SIZE;
            params.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {

        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();


                }
            }
        }
    }


}
