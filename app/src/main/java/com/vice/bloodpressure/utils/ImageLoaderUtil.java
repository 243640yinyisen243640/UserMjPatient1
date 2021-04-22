package com.vice.bloodpressure.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vice.bloodpressure.GlideAppX;

import java.io.ByteArrayOutputStream;

/**
 * 图片加载框架封装类
 */
public class ImageLoaderUtil {
    public static void loadImgUrl(ImageView v, String url) {
        GlideAppX.with(v.getContext())
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(v);
    }


    public static void loadImg(ImageView v, String url, @DrawableRes int placeholder) {
        GlideAppX.with(v.getContext())
                .load(url)
                .fitCenter()
                .placeholder(placeholder)//图片加载出来前，显示的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有
                .into(v);
    }

    public static void loadIntImg(ImageView v, int url, @DrawableRes int placeholder) {
        GlideAppX.with(v.getContext())
                .load(url)
                .fitCenter()
                .placeholder(placeholder)//图片加载出来前，显示的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有
                .into(v);
    }


    public static void loadCircleImg(ImageView v, String url, @DrawableRes int placeholder) {
        GlideAppX.with(v.getContext())
                .load(url)
                .placeholder(placeholder)
                .circleCrop()
                .dontAnimate()//将加载的动画去掉，防止变形
                .into(v);
    }


    /**
     * 加载bitmap
     *
     * @param v
     * @param bt
     */
    public static void loadBitmap(ImageView v, Bitmap bt) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bt.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] bytes = baos.toByteArray();
        GlideAppX.with(v.getContext())
                .asBitmap()
                .load(bytes)
                .into(v);
    }

    /**
     * 朋友圈加载图片
     *
     * @param v
     * @param url
     * @param placeholder
     */
    public static void loadImgQuanZi(ImageView v, String url, @DrawableRes int placeholder) {
        GlideAppX.with(v.getContext())
                .load(url)
                .centerCrop()
                .placeholder(placeholder)//图片加载出来前，显示的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有
                .into(v);
    }
}
