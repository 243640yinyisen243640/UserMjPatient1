package com.lyd.baselib.utils.glide;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lyd.baselib.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * 描述: Glide加载图片工具类
 * 作者: LYD
 * 创建日期: 2019/4/15 17:19
 */
public class GlideUtils {
    private static final int defaultErrorImg = R.drawable.default_img_round;
    /**
     * 加载矩形图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     */
    public static void loadImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .placeholder(defaultImageResourceId)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     */
    public static void loadRoundImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(SizeUtils.dp2px(5));
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = new RequestOptions().transform(new CenterCrop(), roundedCorners);
        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .placeholder(defaultImageResourceId)
                .apply(options)
                .into(imageView);

    }

    /**
     * 加载自定义圆角图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     * @param radius                 图片圆角数组、按照左、上、右、下的顺序添加，偿长度是4，单位是dp
     */
    public static void loadCustomRoundImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView, int[] radius) {
        if (radius == null || radius.length != 4) {
            loadRoundImage(context, defaultImageResourceId, imagePath, imageView);
        } else {
            int leftTopRadius = SizeUtils.dp2px(radius[0]);
            int rightTopRadius = SizeUtils.dp2px(radius[1]);
            int leftBottomRadius = SizeUtils.dp2px(radius[2]);
            int rightBottomRadius = SizeUtils.dp2px(radius[3]);
            CustomRoundedCorners roundedCorners = new CustomRoundedCorners(leftTopRadius, rightTopRadius, leftBottomRadius, rightBottomRadius);
            RequestOptions options = new RequestOptions().transform(new CenterCrop(), roundedCorners);
            Glide.with(context)
                    .asBitmap()
                    .load(imagePath)
                    .placeholder(defaultImageResourceId)
                    .apply(options)
                    .into(imageView);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     */
    public static void loadCircleImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        RequestOptions options = RequestOptions.circleCropTransform();
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
//                .skipMemoryCache(true);//不做内存缓存
        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .placeholder(defaultImageResourceId)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载动画，只播放一次
     *
     * @param context
     * @param defaultImageResourceId
     * @param imagePath
     * @param imageView
     */
    public static void loadGifImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        Glide.with(context)
                .asGif()
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(defaultImageResourceId)
                .error(defaultImageResourceId)
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        GifDrawable gifDrawable = resource;
                        gifDrawable.setLoopCount(2);
                        imageView.setImageDrawable(resource);
                        gifDrawable.start();
                    }
                });
    }

    /**
     * 清除图片磁盘缓存
     *
     * @param context
     */
    public static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     *
     * @param context
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { // 只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否为gif图片
     *
     * @param imagePath
     * @return
     */
    public static boolean isGif(String imagePath) {
        if (imagePath.endsWith(".gif")) {
            return true;
        }
        return false;
    }


    /**
     * 高斯模糊
     *
     * @param imgUrl   图片路径
     * @param errorImg 默认图片(0为默认,其它为自设置)
     * @param img      显示图片的目标
     */
    public static void loadBlur(String imgUrl, int errorImg, ImageView img) {
        Glide.with(Utils.getApp())
                .load(imgUrl)
                .placeholder(errorImg <= 0 ? defaultErrorImg : errorImg)
                .error(errorImg <= 0 ? defaultErrorImg : errorImg)
                .apply(bitmapTransform(new BlurTransformation(25)))
                .into(img);
    }


}
