package com.lyd.baselib.utils.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

public class CustomRoundedCorners extends BitmapTransformation {
    private static final String ID = "com.bumptech.glide.load.resource.bitmap.CustomRoundedCorners";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
    /**
     * 单位像素
     */
    private int leftTop = 10;
    private int rightTop = 10;
    private int leftBottom = 10;
    private int rightBottom = 10;

    /**
     *
     * @param leftTopRadius
     * @param rightTopRadius
     * @param leftBottomRadius
     * @param rightBottomRadius
     */
    public CustomRoundedCorners(int leftTopRadius, int rightTopRadius, int leftBottomRadius, int rightBottomRadius) {
        leftTop = leftTopRadius;
        rightTop = rightTopRadius;
        leftBottom = leftBottomRadius;
        rightBottom = rightBottomRadius;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP));
        RectF rect=new RectF(0f, 0f, width, height);
        float[] radii=new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom};
        Path path = new Path();
        path.addRoundRect(rect, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
        return bitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
