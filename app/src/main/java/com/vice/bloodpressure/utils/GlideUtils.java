package com.vice.bloodpressure.utils;

import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vice.bloodpressure.R;

public class GlideUtils {

    /**
     * 加载第一秒的帧数作为封面
     * url就是视频的地址
     */
    public static void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(Utils.getApp())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(R.drawable.banneryuanwai)
                                .placeholder(R.drawable.banneryuanwai)
                )
                .load(url)
                .into(imageView);
    }


}
