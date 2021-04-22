package com.vice.bloodpressure.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ResourceUtils;
import com.vice.bloodpressure.R;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2020/10/13 15:17
 */
public class TextProgressBar extends LinearLayout {
    private static final String TAG = "TextProgressBar";
    private ProgressBar pbSport;
    private TextView tvSport;


    public TextProgressBar(Context context) {
        super(context);
        init(context);
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        View inflate = View.inflate(context, R.layout.view_progressbar, this);
        pbSport = inflate.findViewById(R.id.pb_sport);
        tvSport = inflate.findViewById(R.id.tv_sport);
        //动态设置进度条背景
        Drawable draw = ResourceUtils.getDrawable(R.drawable.progressbar_bg);
        draw.setBounds(pbSport.getProgressDrawable().getBounds());
        pbSport.setProgressDrawable(draw);
        //解决onDraw不执行
        setWillNotDraw(false);
    }

    /**
     * 动态设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        pbSport.setProgress(progress);
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("LYD", "onDraw执行");
        int progress = pbSport.getProgress();
        //设置值
        tvSport.setText(progress + "%");
        if (progress == 0) {
            return;
        }
        int pbSportWidth = pbSport.getWidth();
        int marginLeft = 0;
        if (progress > 94) {
            int v = (int) (tvSport.getWidth() * 1.1);
            marginLeft = (int) (pbSport.getLeft() + pbSportWidth * ((progress * 1f) / pbSport.getMax())) - v;
        } else if (progress < 4) {
            marginLeft = (int) (pbSport.getLeft() + pbSportWidth * ((progress * 1f) / pbSport.getMax()));
        } else {
            marginLeft = (int) (pbSport.getLeft() + pbSportWidth * ((progress * 1f) / pbSport.getMax())) - tvSport.getWidth() / 2;
        }
        LinearLayout.LayoutParams lp = (LayoutParams) tvSport.getLayoutParams();
        lp.setMargins(marginLeft, 0, 0, 0);
        tvSport.setLayoutParams(lp);
    }
}
