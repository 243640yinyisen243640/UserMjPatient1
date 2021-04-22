package com.vice.bloodpressure.view.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import cn.jzvd.JzvdStd;


/**
 * 描述:
 * 1.隐藏进度条以及屏蔽手势操作的视频播放器
 * 2.循环播放
 * 作者: LYD
 * 创建日期: 2020/10/18 18:13
 */
public class MyJzvdForSport extends JzvdStd {

    public MyJzvdForSport(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTouchUnableView(context);
    }

    /**
     * @param context 这里创建一个view用于屏蔽触摸事件
     */
    private void initTouchUnableView(Context context) {
        View view = new View(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        addView(view);
    }

    /**
     * 播放的时候隐藏底部导航栏
     */
    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        setAllControlsVisiblity(
                GONE,
                GONE,
                GONE,
                GONE,
                GONE,
                GONE,
                GONE
        );
    }

    //    /**
    //     * 当视频播放完成后自动重新播放
    //     */
    //    @Override
    //    public void onStateAutoComplete() {
    //        super.onStateAutoComplete();
    //        startVideo();
    //    }


}