package com.trip.happy.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoaderInterface;

/**
 * Created by xiajun on 2017/11/27.
 */

public class GlideImageLoader implements ImageLoaderInterface {
    @Override
    public void displayImage(Context context, Object path, View imageView) {
        Glide.with(context).load(path).into((ImageView) imageView);
    }

    @Override
    public View createImageView(Context context) {
        return null;
    }
}
