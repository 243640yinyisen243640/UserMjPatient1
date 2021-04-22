package com.vice.bloodpressure.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 描述: 解决GridView只显示一行,高度不能完全展开(重写onMeasure)
 * 作者: LYD
 * 创建日期: 2018/5/28 15:05
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
