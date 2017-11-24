package com.trip.core.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.trip.core.app.Happy;

/**
 * Created by xiajun on 2017/10/19.
 */

public class DimenUtils {

    public static int getScreenWidth() {
        final Resources resources = Happy.getApplicationContext().getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Happy.getApplicationContext().getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.heightPixels;
    }

}
