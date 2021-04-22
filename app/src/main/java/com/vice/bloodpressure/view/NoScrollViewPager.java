package com.vice.bloodpressure.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 描述: 禁止左右滑动的ViewPager
 * 作者: LYD
 * 创建日期: 2020/9/11 10:31
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNoScroll(boolean noScroll) {
        this.isCanScroll = noScroll;
    }

    @Override

    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override

    public boolean onTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }
    }
}
